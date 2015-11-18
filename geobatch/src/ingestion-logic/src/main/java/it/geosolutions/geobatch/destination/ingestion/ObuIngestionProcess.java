/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.geobatch.destination.ingestion;

import it.geosolutions.geobatch.catalog.impl.TimeFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class ObuIngestionProcess {

        /**
         * Time format component
         */
        private static TimeFormat DEFAULT_TIME_FORMAT = new TimeFormat(null, null, null, null);
        
	public static void main(String[] args) throws IOException, ParseException {
		File importFile = new File("D:\\Develop\\destination\\OBU\\OBU.csv");
		BufferedReader reader = null;
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			reader = new BufferedReader(new FileReader(importFile));
			String line = null;
			int count = 0;
			while((line = reader.readLine()) != null) {
				String[] parts = line.split(";");
				int idSorgente = Integer.parseInt(parts[0]);
				Date dataTrasmissione = null, dataRicezione = null, dataCreazione = null;
				try{
	                            dataTrasmissione = DEFAULT_TIME_FORMAT.getDate(parts[1]);
	                            dataRicezione = DEFAULT_TIME_FORMAT.getDate(parts[2]);
	                            dataCreazione = DEFAULT_TIME_FORMAT.getDate(parts[3]);
				}catch (Exception e){
				    // TODO: handle this exception
				}
				String autista = parts[4];
				String trattore = parts[5];
				String semirimorchio = parts[6];
				String cim = parts[7];
				String ultimoMsg = parts[8];
				Double latitude = Double.parseDouble(parts[9]);
				if(latitude > 90.0) {
					latitude = null;
				}
				Double longitude = Double.parseDouble(parts[10]);
				if(longitude > 900.0) {
					longitude = null;
				}
				String type = parts[11];
				String value = parts[12];
				String originalValue = value;
				Map<String, Object> values = new HashMap<String, Object>();
				
				if(type.equals("01")) {
					int n = Integer.parseInt(value.substring(0, 2), 16);
					value = value.substring(2);
					List<String> subcodes = new ArrayList<String>();
					for(int i = 0; i< n; i++) {
						subcodes.add(value.substring(0, 2));						
						value = value.substring(2);
					}
					for(String subcode : subcodes) {
						if(subcode.equals("01")) {
							values.put("velocita_gps", Integer.parseInt(value.substring(0, 2), 16));
							value = value.substring(2);
						} else if(subcode.equals("02")) {
							values.put("direzione_gps", Integer.parseInt(value.substring(0, 3), 16));
							value = value.substring(3);
						} else if(subcode.equals("03")) {
							values.put("pressione_sospensioni", Integer.parseInt(value.substring(0, 4), 16));
							value = value.substring(4);
						} else if(subcode.equals("04")) {
							values.put("distanza", Long.parseLong(value.substring(0, 8), 16));
							value = value.substring(8);
						} else if(subcode.equals("05")) {
							values.put("velocita_odometro", Integer.parseInt(value.substring(0, 2), 16));
							value = value.substring(2);
						} else if(subcode.equals("06")) {
							values.put("inclinazione_longitudinale", Integer.parseInt(value.substring(0, 2), 16));
							value = value.substring(2);
						} else if(subcode.equals("07")) {
							values.put("inclinazione_trasversale", Integer.parseInt(value.substring(0, 2), 16));
							value = value.substring(2);												
						} else if(subcode.equals("0D")) {
							// TODO: Dati CAN bus trattore
							String prefix = value.substring(0,18);
							value = value.substring(18);
							int len = Integer.parseInt(prefix.substring(16, 18));
							int globallen = 3 * len;
							for(int j = 1; j <= len; j++) {
								globallen += 2* (Integer.parseInt(value.substring(3 * j - 1, 3 * j)));
							}
							value = value.substring(globallen + 2);
						} else if(subcode.equals("0F")) {
							// TODO: quantita prodotto negli scomparti
							int len = Integer.parseInt(value.substring(0, 2), 16);
							value = value.substring(2);
							value = value.substring(5 * len);
						} else if(subcode.equals("10")) {
							// TODO: pressione prodotto
							int len = Integer.parseInt(value.substring(0, 2), 16);
							value = value.substring(2 + 5 * len);	
						} else if(subcode.equals("11")) {
							// TODO: temperatura prodotto
							int len = Integer.parseInt(value.substring(0, 2), 16);
							value = value.substring(2 + 5 * len);	
						} else if(subcode.equals("14")) {
							// TODO: tipo prodotto negli scomparti
							int len = Integer.parseInt(value.substring(0, 2), 16);
							value = value.substring(2);
							value = value.substring(3 * len);
						} else if(subcode.equals("16")) {
							values.put("distanza_grezza", Long.parseLong(value.substring(0, 8), 16));
							value = value.substring(8);
						} else {
							value = "";
						}
					}
				}
				//0;2012-11-20T07:50:09+01:00;2012-11-20T07:50:09+01:00;2012-11-20T07:46:44+01:00;
				//UNKNOWNA;UNKNOWNT;LOM1406;UNKNOWNCIM;0;+47.9977;+011.6758;01;020F140412CB320F943181B42CAF04100200300400
				
				count++;
				int velocita = 0;
				int direzione = 0;
				if(values.containsKey("velocita_gps")) {
					velocita = (Integer)values.get("velocita_gps");
				} else if(values.containsKey("velocita_odometro")) {
					velocita = (Integer)values.get("velocita_odometro");
				}
				if(values.containsKey("direzione_gps")) {
					direzione = (Integer)values.get("direzione_gps");
				}
				System.out.print("insert into siig_geo_obu(id,source,data_trasmissione,data_ricezione,data_creazione,autista,trattore,semirimorchio,cim,geometria,tipo,valore,velocita,direzione) ");
				if(longitude == null) {
					System.out.println("values("+count+","+idSorgente+",'"+outputDateFormat.format(dataTrasmissione)+"','"+outputDateFormat.format(dataRicezione)+"','"+outputDateFormat.format(dataCreazione)+"','"+autista+"','"+trattore+"','"+semirimorchio+"','"+cim+"',null,'"+type+"','"+value+"',"+velocita+","+direzione+");");
				} else {
					System.out.println("values("+count+","+idSorgente+",'"+outputDateFormat.format(dataTrasmissione)+"','"+outputDateFormat.format(dataRicezione)+"','"+outputDateFormat.format(dataCreazione)+"','"+autista+"','"+trattore+"','"+semirimorchio+"','"+cim+"',ST_GeomFromText('POINT("+longitude+" "+latitude+")',4326),'"+type+"','"+value+"',"+velocita+","+direzione+");");
				}
				
			}
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}
}
