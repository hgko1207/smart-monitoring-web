package net.woori.start.service;

import java.util.List;

import net.woori.start.domain.db.Measurement;

public interface MeasurementService extends CRUDService<Measurement, Integer> {

	List<Measurement> getList(int pointSq, String startDate, String endDate);
}
