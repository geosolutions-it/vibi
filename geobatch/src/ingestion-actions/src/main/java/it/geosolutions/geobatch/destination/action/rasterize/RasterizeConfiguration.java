package it.geosolutions.geobatch.destination.action.rasterize;

import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

public class RasterizeConfiguration extends ActionConfiguration {

	private String closePhase;
	
	public RasterizeConfiguration(String id, String name, String description) {
		super(id, name, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RasterizeConfiguration clone() {
		final RasterizeConfiguration configuration = (RasterizeConfiguration) super.clone();

		return configuration;
	}
	
	private String baseOutputPath;

	public String getBaseOutputPath() {
		return baseOutputPath;
	}

	public void setBaseOutputPath(String baseOutputPath) {
		this.baseOutputPath = baseOutputPath;
	}

	/**
	 * @return the closePhase
	 */
	public String getClosePhase() {
		return closePhase;
	}

	/**
	 * @param closePhase the closePhase to set
	 */
	public void setClosePhase(String closePhase) {
		this.closePhase = closePhase;
	}
	
	

}
