package net.woori.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.db.Measurement;

public interface MeasurementRepository extends DefaultRepository<Measurement, Integer> {
	
	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<Measurement> findByPointSq(String startDate, String endDate, int pointSq);

	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<Measurement> findByAll(String startDate, String endDate);

	final String SELECT = "DATE(meas_dt) date, AVG(temp_ch1) temp1, AVG(temp_ch2) temp2, AVG(temp_ch3) temp3, "
			+ "AVG(vwc_ch1) water1, AVG(vwc_ch2) water2, AVG(vwc_ch3) water3";
	
	@Query(value = "SELECT " + SELECT + " FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + GROUP_BY, nativeQuery = true)
	List<ChartData> getDailyList(String startDate, String endDate, int pointSq);

}
