package it.geosolutions.geobatch.destination.action.changevalue;

import it.geosolutions.geobatch.actions.ds2ds.Ds2dsConfiguration;

public class ChangeValueConfiguration extends Ds2dsConfiguration {

	private String filter;
	private String attribute;
	private String value;
	
	public ChangeValueConfiguration(String id, String name, String description) {
		super(id, name, description);
		// TODO Auto-generated constructor stub
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
	

}
