/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.destination.ingestion.gate.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Transit bean definition
 * 
 * @author adiaz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Transit")
public class Transit implements Serializable {

/** serialVersionUID */
private static final long serialVersionUID = -1771691529212845397L;

private Long IdGate;

private Long IdTransito;

private String DataRilevamento;

private Integer Corsia;

private String Direzione;

private String KemlerCode;

private String OnuCode;

/**
 * @return the idGate
 */
@XmlElement(name="GateId")
public Long getIdGate() {
    return IdGate;
}

/**
 * @param idGate the idGate to set
 */
public void setIdGate(Long idGate) {
    IdGate = idGate;
}

/**
 * @return the idTransito
 */
@XmlElement(name = "TransitId")
public Long getIdTransito() {
    return IdTransito;
}

/**
 * @param idTransito the idTransito to set
 */
public void setIdTransito(Long idTransito) {
    IdTransito = idTransito;
}

/**
 * @return the dataRilevamento
 */
@XmlElement(name="Timestamp")
public String getDataRilevamento() {
    return DataRilevamento;
}

/**
 * @param dataRilevamento the dataRilevamento to set
 */
public void setDataRilevamento(String dataRilevamento) {
    DataRilevamento = dataRilevamento;
}

/**
 * @return the corsia
 */
@XmlElement(name="Lane")
public Integer getCorsia() {
    return Corsia;
}

/**
 * @param corsia the corsia to set
 */
public void setCorsia(Integer corsia) {
    Corsia = corsia;
}

/**
 * @return the direzione
 */
@XmlElement(name="Direction")
public String getDirezione() {
    return Direzione;
}

/**
 * @param direzione the direzione to set
 */
public void setDirezione(String direzione) {
    Direzione = direzione;
}

/**
 * @return the kemlerCode
 */
@XmlElement(name="KemlerCode")
public String getKemlerCode() {
    return KemlerCode;
}

/**
 * @param kemlerCode the kemlerCode to set
 */
public void setKemlerCode(String kemlerCode) {
    KemlerCode = kemlerCode;
}

/**
 * @return the onuCode
 */
@XmlElement(name="OnuCode")
public String getOnuCode() {
    return OnuCode;
}

/**
 * @param onuCode the onuCode to set
 */
public void setOnuCode(String onuCode) {
    OnuCode = onuCode;
}

}