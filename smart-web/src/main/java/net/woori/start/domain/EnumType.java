package net.woori.start.domain;

import lombok.Getter;

public class EnumType {

	@Getter
	public enum SensorType {
		토양온도("°C"),
		토양수분("%");
		
		private String unit;
		
		private SensorType(String unit) {
			this.unit = unit;
		}
	}
	
	public enum LocationType {
		상층, 중층, 하층;
	}
	
	public enum ChartType {
		일별, 주별, 월별;
	}
}
