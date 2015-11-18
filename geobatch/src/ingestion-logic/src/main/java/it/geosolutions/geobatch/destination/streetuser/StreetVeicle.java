package it.geosolutions.geobatch.destination.streetuser;

import java.util.HashMap;
import java.util.Map;

public class StreetVeicle {
	
	private Integer type;
	private String typeDescription;
	private Double density;
	private Integer meanVelocity;
	private Double occupationCoeff;
	private Map<StreetScenario,ComputedData> scenarioComputedData = new HashMap<StreetScenario, ComputedData>();
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getDensity() {
		return density;
	}
	public void setDensity(Double density) {
		this.density = density;
	}
	public Integer getMeanVelocity() {
		return meanVelocity;
	}
	public void setMeanVelocity(Integer meanVelocity) {
		this.meanVelocity = meanVelocity;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	public Double getOccupationCoeff() {
		return occupationCoeff;
	}
	public void setOccupationCoeff(Double occupationCoeff) {
		this.occupationCoeff = occupationCoeff;
	}
	
	public void addComputedData(StreetScenario scenario ,ComputedData data){
		this.scenarioComputedData.put(scenario, data);
	}
	
	public ComputedData getComputedData(StreetScenario scenario) {
		return this.scenarioComputedData.get(scenario);
	}
	public void resetComputedData() {
		this.scenarioComputedData.clear();
	}
	
	
	
}
