package net.woori.start.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineChartSeries {

	private String name;
	
	private String type;
	
	private boolean smooth;
	
	private int symbolSize;

	private List<Float> data;
	
	public LineChartSeries(String name) {
		this.name = name;
		this.type = "line";
		this.smooth = true;
		this.symbolSize = 3;

		data = new ArrayList<>();
	}

	public void addDataItem(float value) {
		data.add(value);
	}

}
