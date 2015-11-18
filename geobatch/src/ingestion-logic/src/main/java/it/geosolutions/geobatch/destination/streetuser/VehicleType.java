package it.geosolutions.geobatch.destination.streetuser;

public class VehicleType {
	int type;
	String description;
	double coefficienteOccupazione;
	public VehicleType(int type, String description,
			double coefficienteOccupazione) {
		super();
		this.type = type;
		this.description = description;
		this.coefficienteOccupazione = coefficienteOccupazione;
	}
	public int getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public double getCoefficienteOccupazione() {
		return coefficienteOccupazione;
	}
	
	
}
