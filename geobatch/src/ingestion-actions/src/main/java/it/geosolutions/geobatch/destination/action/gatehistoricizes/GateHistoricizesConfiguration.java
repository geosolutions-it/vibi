package it.geosolutions.geobatch.destination.action.gatehistoricizes;

import it.geosolutions.geobatch.actions.ds2ds.Ds2dsConfiguration;

public class GateHistoricizesConfiguration extends Ds2dsConfiguration {
	
	private Integer beforeYear;
	private Integer beforeMonth;
	private Integer beforeDay;
	
	public GateHistoricizesConfiguration(String id, String name, String description) {
		super(id, name, description);
	}

	public Integer getBeforeYear() {
		return beforeYear == null ? 0 : beforeYear;
	}

	public void setBeforeYear(Integer beforeYear) {
		this.beforeYear = beforeYear;
	}

	public Integer getBeforeMonth() {
		return beforeMonth == null ? 0 : beforeMonth;
	}

	public void setBeforeMonth(Integer beforeMonth) {
		this.beforeMonth = beforeMonth;
	}

	public Integer getBeforeDay() {
		return beforeDay == null ? 0 : beforeDay;
	}

	public void setBeforeDay(Integer beforeDay) {
		this.beforeDay = beforeDay;
	}
	
	

}
