package it.geosolutions.geobatch.destination.ingestion;

import java.io.Serializable;

public class CodiceProvincialeComunale implements Serializable {
        private String codiceComune;
        private String codiceProvincia;
        
        public String getCodiceComune() {
                return codiceComune;
        }
        public void setCodiceComune(String codiceComune) {
                this.codiceComune = codiceComune;
        }
        public String getCodiceProvincia() {
                return codiceProvincia;
        }
        public void setCodiceProvincia(String codiceProvincia) {
                this.codiceProvincia = codiceProvincia;
        }
 
}
