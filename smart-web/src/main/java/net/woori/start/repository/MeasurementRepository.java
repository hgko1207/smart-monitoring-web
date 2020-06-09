package net.woori.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.start.domain.db.Measurement;

public interface MeasurementRepository extends DefaultRepository<Measurement, Integer> {
	
	final String BETWEEN = "meas_dt BETWEEN to_timestamp(?2, 'YYYY-MM-DD HH24:MI:SS') and to_timestamp(?3, 'YYYY-MM-DD HH24:MI:SS')";
	final String ORDER_BY = " ORDER BY meas_dt";

	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE point_sq = ?1 and " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<Measurement> findByPointSq(int pointSq, String startDate, String endDate);

}
