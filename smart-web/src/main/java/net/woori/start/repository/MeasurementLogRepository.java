package net.woori.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.db.MeasurementLog;

public interface MeasurementLogRepository extends DefaultRepository<MeasurementLog, Integer> {
	
	final String SELECT_DAILY = "DATE(meas_dt) date, AVG(temp_ch1) temp1, AVG(temp_ch2) temp2, AVG(temp_ch3) temp3, "
			+ "AVG(vwc_ch1) water1, AVG(vwc_ch2) water2, AVG(vwc_ch3) water3";
	final String SELECT_HOURLY = "TO_TIMESTAMP(to_char(meas_dt, 'YYYY-MM-DD HH24:00:00'), 'YYYY-MM-DD HH24:MI:SS') date, "
			+ "AVG(temp_ch1) temp1, AVG(temp_ch2) temp2, AVG(temp_ch3) temp3, "
			+ "AVG(vwc_ch1) water1, AVG(vwc_ch2) water2, AVG(vwc_ch3) water3";
	
	final String BETWEEN = "meas_dt BETWEEN to_timestamp(?1, 'YYYY-MM-DD HH24:MI:SS') and to_timestamp(?2, 'YYYY-MM-DD HH24:MI:SS') + interval '1'";
	
	final String GROUP_BY_DAILY = " GROUP BY DATE(meas_dt)";
	final String GROUP_BY_HOURLY = " GROUP BY date";
	
	final String ORDER_BY = " ORDER BY meas_dt";
	final String ORDER_BY_DATE = " ORDER BY date";
	
	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<MeasurementLog> findByPointSq(String startDate, String endDate, int pointSq);

	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<MeasurementLog> findByAll(String startDate, String endDate);
	
	@Query(value = "SELECT " + SELECT_DAILY + " FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + GROUP_BY_DAILY + ORDER_BY_DATE, nativeQuery = true)
	List<ChartData> getDailyList(String startDate, String endDate, int pointSq);
	
	@Query(value = "SELECT " + SELECT_HOURLY + " FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + GROUP_BY_HOURLY + ORDER_BY_DATE, nativeQuery = true)
	List<ChartData> getHourlyList(String startDate, String endDate, int pointSq);

	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE point_sq = ?1 ORDER By meas_dt limit 1", nativeQuery = true)
	MeasurementLog getCurrentData(int pointSq);

	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + ORDER_BY + " limit 1", nativeQuery = true)
	MeasurementLog getCurrentData(String startDate, String endDate, int pointSq);

}
