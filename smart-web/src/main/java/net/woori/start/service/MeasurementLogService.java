package net.woori.start.service;

import java.util.List;

import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.db.MeasurementLog;

public interface MeasurementLogService extends CRUDService<MeasurementLog, Integer> {

	List<MeasurementLog> getList(int pointSq, String startDate, String endDate);
	
	List<ChartData> getDailyList(int pointSq, String startDate, String endDate);
	
	List<ChartData> getHourlyList(int pointSq, String startDate, String endDate);
	
	MeasurementLog getCurrentData(int pointSq);
	
	MeasurementLog getCurrentData(int pointSq, String startDate, String endDate);
}
