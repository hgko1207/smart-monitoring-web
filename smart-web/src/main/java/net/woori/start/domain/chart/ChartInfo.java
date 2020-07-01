package net.woori.start.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.woori.start.domain.EnumType.SensorPointType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.EnumType.WeatherType;

/**
 * LineChart 정보
 * 
 * @author hgko
 *
 */
@Data
public class ChartInfo {
	
	private String unit;

	private String title;
	
	private List<String> categories;
	
	private List<LineChartSeries> lineChartSeries;
	
	private List<BarChartSeries> barChartSeries;
	
	public ChartInfo() {
		categories = new ArrayList<>();
		barChartSeries = new ArrayList<>();
	}
	
	public ChartInfo(SensorType type) {
		this.title = type.name();
		this.unit = type.getUnit();
		categories = new ArrayList<>();
		lineChartSeries = new ArrayList<>();
	}
	
	public ChartInfo(WeatherType type) {
		this.title = "";
		this.unit = type.getUnit();
		categories = new ArrayList<>();
		lineChartSeries = new ArrayList<>();
	}
	
	public ChartInfo(SensorType type, SensorPointType sensorPoint) {
		this(type);
		this.title = type.name() + " " + sensorPoint.name();
	}
	
	public void addCategory(String value) {
		categories.add(value);
	}
	
	public void addBarChartSeries(BarChartSeries series) {
		barChartSeries.add(series);
	}
	
	public void addListChartSeries(LineChartSeries series) {
		lineChartSeries.add(series);
	}
}
