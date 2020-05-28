package net.woori.start.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * LineChart 정보
 * 
 * @author hgko
 *
 */
@Data
public class ChartInfo {

	private String title;
	
	private List<String> categories;
	
	private List<BarChartSeries> barChartSeries;
	
	public ChartInfo() {
		categories = new ArrayList<>();
		barChartSeries = new ArrayList<>();
	}
	
	public void addCategory(String value) {
		categories.add(value);
	}
	
	public void addBarChartSeries(BarChartSeries series) {
		barChartSeries.add(series);
	}
}
