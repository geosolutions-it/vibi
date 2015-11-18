/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2013, Open Source Geospatial Foundation (OSGeo)
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
package it.geosolutions.geobatch.destination.ingestion.gate.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Bean declaration for a statistic object
 * 
 * @author adiaz
 */
public class StatisticsBean implements Serializable {

/**
 * Generated UID 
 */
private static final long serialVersionUID = 1558958354979587410L;

private BigDecimal fk_gate;
private int fk_interval;
private Timestamp data_stat_inizio;
private Timestamp data_stat_fine;
private BigDecimal flg_corsia;
private BigDecimal direzione;
private String codice_kemler;
private String codice_onu;
private BigDecimal quantita;

/**
 * @return the fk_gate
 */
public BigDecimal getFk_gate() {
    return fk_gate;
}

/**
 * @param fk_gate the fk_gate to set
 */
public void setFk_gate(BigDecimal fk_gate) {
    this.fk_gate = fk_gate;
}

/**
 * @return the fk_interval
 */
public int getFk_interval() {
    return fk_interval;
}

/**
 * @param fk_interval the fk_interval to set
 */
public void setFk_interval(int fk_interval) {
    this.fk_interval = fk_interval;
}

/**
 * @return the data_stat_inizio
 */
public Timestamp getData_stat_inizio() {
    return data_stat_inizio;
}

/**
 * @param data_stat_inizio the data_stat_inizio to set
 */
public void setData_stat_inizio(Timestamp data_stat_inizio) {
    this.data_stat_inizio = data_stat_inizio;
}

/**
 * @return the data_stat_fine
 */
public Timestamp getData_stat_fine() {
    return data_stat_fine;
}

/**
 * @param data_stat_fine the data_stat_fine to set
 */
public void setData_stat_fine(Timestamp data_stat_fine) {
    this.data_stat_fine = data_stat_fine;
}

/**
 * @return the flg_corsia
 */
public BigDecimal getFlg_corsia() {
    return flg_corsia;
}

/**
 * @param flg_corsia the flg_corsia to set
 */
public void setFlg_corsia(BigDecimal flg_corsia) {
    this.flg_corsia = flg_corsia;
}

/**
 * @return the direzione
 */
public BigDecimal getDirezione() {
    return direzione;
}

/**
 * @param direzione the direzione to set
 */
public void setDirezione(BigDecimal direzione) {
    this.direzione = direzione;
}

/**
 * @return the codice_kemler
 */
public String getCodice_kemler() {
    return codice_kemler;
}

/**
 * @param codice_kemler the codice_kemler to set
 */
public void setCodice_kemler(String codice_kemler) {
    this.codice_kemler = codice_kemler;
}

/**
 * @return the codice_onu
 */
public String getCodice_onu() {
    return codice_onu;
}

/**
 * @param codice_onu the codice_onu to set
 */
public void setCodice_onu(String codice_onu) {
    this.codice_onu = codice_onu;
}

/**
 * @return the quantita
 */
public BigDecimal getQuantita() {
    return quantita;
}

/**
 * @param quantita the quantita to set
 */
public void setQuantita(BigDecimal quantita) {
    this.quantita = quantita;
}

}
