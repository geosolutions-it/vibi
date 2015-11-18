package it.geosolutions.geobatch.destination.streetuser;

public class StreetUserResult {
	Integer idArco;
	Integer idDistanza;
	Integer idScenario;
	
	Double utentiSede;
	Double utentiBersaglio;
	
	public Integer getIdArco() {
		return idArco;
	}
	public void setIdArco(Integer idArco) {
		this.idArco = idArco;
	}
	public Integer getIdDistanza() {
		return idDistanza;
	}
	public void setIdDistanza(Integer idDistanza) {
		this.idDistanza = idDistanza;
	}
	public Integer getIdScenario() {
		return idScenario;
	}
	public void setIdScenario(Integer idScenario) {
		this.idScenario = idScenario;
	}
	public Double getUtentiSede() {
		return utentiSede;
	}
	public void setUtentiSede(Double utentiSede) {
		this.utentiSede = utentiSede;
	}
	public Double getUtentiBersaglio() {
		return utentiBersaglio;
	}
	public void setUtentiBersaglio(Double utentiBersaglio) {
		this.utentiBersaglio = utentiBersaglio;
	}
	public StreetUserResult(Integer idArco, Integer idDistanza,
			Integer idScenario, Double utentiSede, Double utentiBersaglio) {
		super();
		this.idArco = idArco;
		this.idDistanza = idDistanza;
		this.idScenario = idScenario;
		this.utentiSede = utentiSede;
		this.utentiBersaglio = utentiBersaglio;
	}
	
	
}
