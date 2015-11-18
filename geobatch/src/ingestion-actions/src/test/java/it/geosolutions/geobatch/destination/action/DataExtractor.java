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
package it.geosolutions.geobatch.destination.action;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class DataExtractor {

        static FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Map<String, Serializable> datastoreParams = new HashMap<String, Serializable>();
        datastoreParams.put("port", 5432);
        datastoreParams.put("schema", "siig_p");
        datastoreParams.put("passwd", "siig_p");
        datastoreParams.put("dbtype", "postgis");
        datastoreParams.put("host", "localhost");
        datastoreParams.put("Expose primary keys", "true");
        datastoreParams.put("user", "siig_p");
        datastoreParams.put("database", "destination_staging");
        
        String[] typeNames = new String[] {
        		"siig_geo_pl_province"
        		/*
        		"siig_d_patrimonialita_strada",
        		"siig_t_incidentalita",
        		"siig_d_categoria_strada"
        		
        		
        		"siig_geo_grid",
        		"siig_d_bene_culturale",
        		"siig_d_classe_adr",
        		"siig_d_classe_clc",
        		"siig_d_dissesto",
        		"siig_d_distanza",
        		"siig_d_gravita",
        		"siig_d_iucn",
        		"siig_d_partner",
        		"siig_d_stato_fisico",
        		"siig_d_tipo_captazione",
        		"siig_d_tipo_contenitore",
        		"siig_d_tipo_trasporto",
        		"siig_d_tipo_uso",
        		"siig_d_tipo_variabile",
        		"siig_d_tipo_veicolo",
        		"siig_d_tipologia_danno",
        		"siig_mtd_d_arco",
        		"siig_mtd_d_criterio_filtro",
        		"siig_mtd_d_elaborazione",
        		"siig_mtd_r_formula_criterio",
        		"siig_mtd_r_formula_elab",
        		"siig_mtd_r_formula_formula",
        		"siig_mtd_r_formula_parametro",
        		"siig_mtd_r_param_bers_arco",
        		"siig_mtd_t_bersaglio",
        		"siig_mtd_t_formula",
        		"siig_mtd_t_parametro",
        		"siig_r_area_danno",
        		"siig_r_scenario_gravita",
        		"siig_r_scenario_sostanza",
        		"siig_t_bersaglio",
        		"siig_t_scenario",
        		"siig_t_sostanza",
        		"siig_t_variabile"*/
        };
        
        JDBCDataStore dataStore = null;
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("j:/develop/province.txt")), true));
        try {
        	dataStore = (JDBCDataStore)DataStoreFinder.getDataStore(datastoreParams);
	        for(String typeName : typeNames) {
	        		Filter filter = ff.equals(ff.property("cod_provincia"),ff.literal("021"));
	        	SimpleFeatureIterator iterator = null;
	        	try {
		        	iterator = dataStore.getFeatureSource(typeName).getFeatures(filter).features();
		        	while(iterator.hasNext()) {
		        		SimpleFeature feature = iterator.next();
		        		System.out.println(typeName+"="+DataUtilities.encodeFeature(feature, true));
		        	}
		        	
	        	} finally {
	        		if(iterator != null) {
	        			iterator.close();
	        		}
	        	}
	        }
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
        	if(dataStore != null) {
        		dataStore.dispose();
        	}
        	
        }

	}

}
