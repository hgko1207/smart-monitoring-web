package net.woori.start.domain;

import lombok.Data;
import net.woori.start.domain.EnumType.LevelType;
import net.woori.start.domain.chart.ChartInfo;

@Data
public class DashboardInfo {
	
	private String dateTime;
	
	/** 날씨 정보 */
	private WeatherInfo weatherInfo;
	
	/** 양호, 주의, 경계, 심각 */
	private LevelType levelType;

	private ChartInfo soilABarChart;
	
	private ChartInfo soilBBarChart;
	
	private ChartInfo soilCBarChart;
	
	private ChartInfo soilDBarChart;
	
	private ChartInfo soilEBarChart;
	
	private ChartInfo tempALineChart;
	
	private ChartInfo waterALineChart;
	
	private ChartInfo tempBLineChart;
	
	private ChartInfo waterBLineChart;
	
	private ChartInfo tempCLineChart;
	
	private ChartInfo waterCLineChart;
	
	private ChartInfo tempDLineChart;
	
	private ChartInfo waterDLineChart;
	
	private ChartInfo tempELineChart;
	
	private ChartInfo waterELineChart;
}
