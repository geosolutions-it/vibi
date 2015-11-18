package it.geosolutions.geobatch.destination.streetuser;

public class ComputedData {

	private StreetDistance streetDistance;
	private StreetScenario streetScenario;
	private StreetInfo streetInfo;
	private StreetVeicle streetVeicle;
	private Double n_territoriali;
	private Double n_transito;
	private Double n_coda;
	
	public Double getN_territoriali() {
		return n_territoriali;
	}
	public void setN_territoriali(Double n_territoriali) {
		this.n_territoriali = n_territoriali;
	}
	public Double getN_transito() {
		return n_transito;
	}
	public void setN_transito(Double n_transito) {
		this.n_transito = n_transito;
	}
	public Double getN_coda() {
		return n_coda;
	}
	public void setN_coda(Double n_coda) {
		this.n_coda = n_coda;
	}
	
	
	
}
