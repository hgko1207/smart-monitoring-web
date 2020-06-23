package net.woori.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.start.domain.db.Measurement;

public interface MeasurementRepository extends DefaultRepository<Measurement, Integer> {
	
	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<Measurement> findByPointSq(String startDate, String endDate, int pointSq);

	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<Measurement> findByAll(String startDate, String endDate);

	@Query(value = "SELECT * FROM tb_sf_meas_raw WHERE point_sq = ?3 and " + BETWEEN + ORDER_BY, nativeQuery = true)
	List<Measurement> getDailyList(String startDate, String endDate, int pointSq);

}
