package net.woori.start.service;

import java.util.List;

import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.db.Measurement;

public interface MeasurementService extends CRUDService<Measurement, Integer> {

	List<Measurement> getList(String startDate, String endDate);
	
	List<Measurement> getList(int pointSq, String startDate, String endDate);
	
	List<ChartData> getDailyList(int pointSq, String startDate, String endDate);
}
