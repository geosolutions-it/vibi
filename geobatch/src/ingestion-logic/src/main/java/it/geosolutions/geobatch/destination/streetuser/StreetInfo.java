package it.geosolutions.geobatch.destination.streetuser;

import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

public class StreetInfo {
	
	private Boolean originStreet;
	private SimpleFeature originFeature;
	private Geometry effectiveGeometry;
	private Double storage;
	private List<StreetVeicle> veicleTypes = new ArrayList<StreetVeicle>();

	public StreetInfo(SimpleFeature originFeature, Geometry effectiveGeometry, Boolean originStreet, Double storage) {
		super();
		this.originFeature = originFeature;
		this.effectiveGeometry = effectiveGeometry;
		this.originStreet = originStreet;
		this.storage = storage;
	}

	public SimpleFeature getOriginFeature() {
		return originFeature;
	}

	public Geometry getEffectiveGeometry() {
		return effectiveGeometry;
	}
	
	public void addVeicleType(StreetVeicle veicleType){
		veicleTypes.add(veicleType);
	}
	
	public List<StreetVeicle> getVeicleTypes() {
		return veicleTypes;
	}

	public Boolean getOriginStreet() {
		return originStreet;
	}

	public void setOriginStreet(Boolean originStreet) {
		this.originStreet = originStreet;
	}
	
	public Double getStorage() {
		return storage;
	}

	public void resetVehiclesData() {
		for(StreetVeicle vehicle : veicleTypes) {
			vehicle.resetComputedData();
		}
	}

}
