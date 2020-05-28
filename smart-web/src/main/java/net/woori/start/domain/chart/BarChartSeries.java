package net.woori.start.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BarChartSeries {

	private String name;
	
	private String type;
	
	private List<BarChartData> data;
	
	private BarChartItemStyle itemStyle;
	
	public BarChartSeries(String name) {
		this.name = name;
		this.type = "bar";
		this.itemStyle = new BarChartItemStyle();

		data = new ArrayList<>();
	}

	public void addDataItem(float value) {
		data.add(new BarChartData(value));
	}
	
	@Data
	@NoArgsConstructor
	public class BarChartData {
		
		private float value;
		
		private ItemStyle itemStyle;
		
		public BarChartData(float value) {
			String color = "";
			if (value >= 40) {
				color = "#ff9710";
			} else if (value >= 20) {
				color = "#00d76a";
			} else if (value >= 10) {
				color = "#ffeb0e";
			} else if (value >= 0) {
				color = "#ff2e2f";
			}
			
			this.value = value;
			this.itemStyle = new ItemStyle(color);
		}
	}
	
	@Getter
	@AllArgsConstructor
	public class ItemStyle {
		
		private String color;
		
	}
	
	@Getter
	public class Label {
		
		private String color;
	}
}
