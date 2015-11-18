package it.geosolutions.geobatch.destination.streetuser;

import com.vividsolutions.jts.geom.Geometry;

public class StreetUser {
	private Integer idArco;
	private Geometry geoemtry;
	private int distance;
	public StreetUser(Integer idArco, Geometry geoemtry, int distance) {
		super();
		this.idArco = idArco;
		this.distance = distance;
		this.geoemtry = geoemtry;
	}
	
	public Integer getIdArco() {
		return idArco;
	}



	public void setIdArco(Integer idArco) {
		this.idArco = idArco;
	}



	public Geometry getGeoemtry() {
		return geoemtry;
	}



	public void setGeoemtry(Geometry geoemtry) {
		this.geoemtry = geoemtry;
	}



	public int getDistance() {
		return distance;
	}
	
}
