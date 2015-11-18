/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.destination.ingestion.gate.statistics;

import it.geosolutions.geobatch.catalog.impl.TimeFormat;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.jdbc.JDBCDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Statistics extractor based on a simple SQL query
 * 
 * @author adiaz
 */
public class StatisticsJDBCExtractor implements StatisticsExtractor {

private final static Logger LOGGER = LoggerFactory
        .getLogger(StatisticsJDBCExtractor.class);

/**
 * DataStore to make the query
 */
private JDBCDataStore dataStore;

/**
 * Key for the time start
 */
private static final String START_KEY = ":start";

/**
 * Key for the time end
 */
private static final String END_KEY = ":end";

/**
 * SQL query to be launched for extract data
 */
private static String JOIN_SQL = "select count(*) as quantita, fk_gate, "
        + "flg_corsia, flg_direzione, codice_onu from siig_gate_t_dato "
        + "where data_rilevamento between '" + START_KEY + "' and '" + END_KEY
        + "' group by fk_gate, flg_corsia, flg_direzione, codice_onu";

/**
 * Time format component
 */
private TimeFormat timeFormat;

/**
 * Create a JDBC statistics extractor
 * 
 * @param dataStore to be used to execute the known query
 */
public StatisticsJDBCExtractor(JDBCDataStore dataStore, TimeFormat timeFormat) {
    super();
    this.dataStore = dataStore;
    this.timeFormat = timeFormat;
}

/*
 * (non-Javadoc)
 * @see it.geosolutions.geobatch.destination.ingestion.gate.statistics.
 * StatisticsExtractor
 * #getStatistics(it.geosolutions.geobatch.destination.ingestion
 * .gate.statistics.StatisticInterval)
 */
@Override
public List<StatisticsBean> getStatistics(StatisticInterval interval)
        throws IOException {
    Timestamp start = null, end = null;
    switch (interval) {
    case LAST_DAY:
        start = timeFormat.getTodayStartTime();
        break;
    case LAST_WEEK:
        start = timeFormat.getPreviousWeekSpan();
        break;
    case LAST_MONTH:
        start = timeFormat.getPreviousMonthSpan();
        break;
    case LAST_YEAR:
        start = timeFormat.getPreviousYearSpan();
        break;
    default:
        start = timeFormat.getTodayStartTime();
    }
    end = timeFormat.getTodayEndTime();

    return getStatistics(start, end, interval);
}

/*
 * (non-Javadoc)
 * @see it.geosolutions.geobatch.destination.ingestion.gate.statistics.
 * StatisticsExtractor#getStatistics()
 */
@Override
public List<StatisticsBean> getStatistics() throws IOException {
    List<StatisticsBean> all = new LinkedList<StatisticsBean>();

    // create time stamps
    Timestamp end = timeFormat.getTodayEndTime();
    Timestamp startDay = timeFormat.getTodayStartTime();
    Timestamp startWeek = timeFormat.getPreviousWeekSpan();
    Timestamp startMonth = timeFormat.getPreviousMonthSpan();
    Timestamp startYear = timeFormat.getPreviousYearSpan();

    // obtain data for each interval
    all.addAll(getStatistics(startDay, end, StatisticInterval.LAST_DAY));
    all.addAll(getStatistics(startWeek, end, StatisticInterval.LAST_WEEK));
    all.addAll(getStatistics(startMonth, end, StatisticInterval.LAST_MONTH));
    all.addAll(getStatistics(startYear, end, StatisticInterval.LAST_YEAR));

    return all;
}

/**
 * Obtain data for a interval
 * 
 * @param start time stamp with the start time
 * @param end time stamp with the end time
 * @param interval type of interval
 * @return collected data for the interval
 * @throws IOException
 */
private List<StatisticsBean> getStatistics(Timestamp start, Timestamp end,
        StatisticInterval interval) throws IOException {
    List<StatisticsBean> result = null;
    if (start != null && end != null) {
        Transaction transaction = null;
        Connection conn = null;
        try {
            transaction = new DefaultTransaction();
            conn = dataStore.getConnection(transaction);
            result = getStatistics(conn, transaction, start, end, interval);
        } catch (SQLException e) {
            throw new IOException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new IOException(e);
                }
            }
            if (transaction != null) {
                transaction.close();
            }
        }
    }

    return result;
}

/**
 * Obtain a list of statistics for an interval
 * 
 * @param conn
 * @param transaction
 * @param start timestamp with the start of the interval
 * @param end timestamp with the end of the interval
 * @param interval type of interval
 * @return
 * @throws IOException
 * @throws SQLException
 */
private List<StatisticsBean> getStatistics(Connection conn,
        Transaction transaction, Timestamp start, Timestamp end,
        StatisticInterval interval) throws IOException, SQLException {
    List<StatisticsBean> list = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = JOIN_SQL.replace(START_KEY, start.toString()).replace(END_KEY,
            end.toString());
    try {
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        list = new LinkedList<StatisticsBean>();
        while (rs.next()) {
            int column = 1;
            // read data from result set
            StatisticsBean bean = new StatisticsBean();
            Object calculatedQuantita = rs.getObject(column++);
            BigDecimal quantita = null;
            if (calculatedQuantita instanceof BigDecimal) {
                quantita = (BigDecimal) calculatedQuantita;
            } else if (calculatedQuantita instanceof Long) {
                quantita = new BigDecimal((Long) calculatedQuantita);
            } else if (calculatedQuantita instanceof Integer) {
                quantita = new BigDecimal((Integer) calculatedQuantita);
            }
            bean.setQuantita(quantita);
            bean.setFk_gate((BigDecimal) rs.getObject(column++));
            bean.setFlg_corsia((BigDecimal) rs.getObject(column++));
            bean.setDirezione((BigDecimal) rs.getObject(column++));
            bean.setCodice_onu((String) rs.getObject(column++));
            bean.setData_stat_inizio(start);
            bean.setData_stat_fine(end);
            bean.setFk_interval(interval.ordinal());
            list.add(bean);
        }
    } catch (SQLException e) {
        LOGGER.error(e.getMessage(), e);
        throw e;

    } finally {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
    }

    return list;
}

}
