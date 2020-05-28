package net.woori.start.domain;

import lombok.Data;
import net.woori.start.domain.chart.ChartInfo;

@Data
public class DashboardInfo {

	private ChartInfo soilABarChart;
	
	private ChartInfo soilBBarChart;
	
	private ChartInfo soilCBarChart;
	
	private ChartInfo soilDBarChart;
	
	private ChartInfo soilEBarChart;
}
