package net.woori.start.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.woori.start.domain.EnumType.LevelType;
import net.woori.start.domain.EnumType.SensorType;

@Data
@NoArgsConstructor
public class BarChartSeries {

	private String name;
	
	private String type;
	
	private int barWidth = 32;
	
	private List<BarChartData> data;
	
	private BarChartItemStyle itemStyle;
	
	private SeriesLabel label = new SeriesLabel();
	
	private SensorType sensorType;
	
	public BarChartSeries(SensorType type, String name) {
		this.name = name;
		this.type = "bar";
		this.itemStyle = new BarChartItemStyle();

		this.sensorType = type;
		data = new ArrayList<>();
	}

	public void addDataItem(float value) {
		data.add(new BarChartData(sensorType, value));
	}
	
	@Data
	public class SeriesLabel {
		
		private Normal normal = new Normal();
		
	}
	
	@Data
	public class Normal {
		
		private boolean show = true;
		
//		private String color = "#353535";
		
		private int fontWeight = 600;

		private String textBorderColor = "#353535";
		
		private int textBorderWidth = 0;
	}
	
	@Data
	@NoArgsConstructor
	public class BarChartData {
		
		private float value;
		
		private Label label = new Label();
		
		private ItemStyle itemStyle;
		
		public BarChartData(SensorType type, float value) {
			String color = "";
			if (type == SensorType.토양수분) {
				if (value > 0 && value < 4.6) {
					color = LevelType.주의.getColor();
				} else if (value >= 4.6 && value < 44) {
					color = LevelType.양호.getColor();
				} else if (value >= 44) {
					color = LevelType.경계.getColor();
				} else {
					color = "#353535";
				}
			} else if (type == SensorType.토양온도) {
			 	if ((value >= 0 && value < 10) || (value >= 35 && value <= 45)) {
					color = LevelType.심각.getColor();
				} else if ((value >= 10 && value < 15) || (value >= 28 && value < 35)) {
					color = LevelType.경계.getColor();
				} else if ((value >= 15 && value < 19) || (value >= 25 && value < 28)) {
					color = LevelType.주의.getColor();
				} else if (value >= 19 && value < 25) {
					color = LevelType.양호.getColor();
				} else {
					color = LevelType.심각.getColor();
				}
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
		
		private String position = "right";
	}
}
