package net.woori.start.service;

import java.util.List;

import net.woori.start.domain.chart.WeatherChartData;
import net.woori.start.domain.db.Weather;

public interface WeatherService extends CRUDService<Weather, Integer> {

	List<WeatherChartData> getChartList(String startDate, String endDate);
	
	List<Weather> getList(String startDate, String endDate);
	
	Weather getRecentData();
}
