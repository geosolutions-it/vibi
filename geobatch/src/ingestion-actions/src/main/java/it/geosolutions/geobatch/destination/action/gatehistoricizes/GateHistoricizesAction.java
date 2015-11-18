package it.geosolutions.geobatch.destination.action.gatehistoricizes;

import it.geosolutions.geobatch.actions.ds2ds.Ds2dsAction;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.catalog.impl.TimeFormat;

import java.io.IOException;
import java.util.Calendar;

@Action(configurationClass = GateHistoricizesConfiguration.class)
public class GateHistoricizesAction extends Ds2dsAction {

    /**
     * Time format component
     */
    private static TimeFormat DEFAULT_TIME_FORMAT = new TimeFormat(null, null, null, null);

	static GateHistoricizesConfiguration preprocessEcqlFilter(GateHistoricizesConfiguration configuration) {
		Calendar calendar = DEFAULT_TIME_FORMAT.getTodayCalendar();
                calendar.roll(Calendar.YEAR, -configuration.getBeforeYear());
                calendar.roll(Calendar.MONTH, -configuration.getBeforeMonth());
                calendar.roll(Calendar.DATE, -configuration.getBeforeDay());
		String ecqlFilter = "data_rilevamento BEFORE " +  DEFAULT_TIME_FORMAT.getDate(calendar.getTime());				
		configuration.setEcqlFilter(ecqlFilter);		
		return configuration;
	}

	public GateHistoricizesAction(GateHistoricizesConfiguration configuration) throws IOException {		
		super(preprocessEcqlFilter(configuration));	
	}

}

