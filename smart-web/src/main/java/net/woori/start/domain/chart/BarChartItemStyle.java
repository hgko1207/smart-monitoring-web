package net.woori.start.domain.chart;

import lombok.Data;
import lombok.Getter;

@Data
public class BarChartItemStyle {

	private Normal normal = new Normal();
	
	@Data
	public class Normal {
		
		private Label label = new Label();
	}
	
	@Getter
	public class Label {
		
		private boolean show = true;
		
		private String position = "insideRight";
		
		private String formatter = "{c}%";
		
		private TextStyle textStyle = new TextStyle();
	}
	
	@Getter
	public class TextStyle {
		
		private int padding = 5;
	}
}
