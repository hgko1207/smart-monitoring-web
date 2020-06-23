package net.woori.start.domain;

import lombok.Getter;

public class EnumType {
	
	@Getter
	public enum PointType {
		A("A", "토양수분 A", "토양온도 A"),
		B("B", "토양수분 B", "토양온도 B"),
		C("C", "토양수분 C", "토양온도 C"),
		D("D", "토양수분 D", "토양온도 D"),
		E("E", "토양수분 E", "토양온도 E");
		
		private String name;
		
		private String titleWater;
		
		private String titleTemp;
		
		private PointType(String name, String titleWater, String titleTemp) {
			this.name = name;
			this.titleWater = titleWater;
			this.titleTemp = titleTemp;
		}
	}

	@Getter
	public enum SensorType {
		토양수분("%"),
		토양온도("°C");
		
		private String unit;
		
		private SensorType(String unit) {
			this.unit = unit;
		}
	}
	
	public enum SensorPointType {
		시점, 말단; 
	}
	
	@Getter
	public enum LocationType {
		상층("10cm"), 
		중층("30cm"), 
		하층("50cm");
		
		private String name;
		
		private LocationType(String name) {
			this.name = name;
		}
	}
	
	public enum ChartType {
		일별, 주별, 월별;
	}
	
	public enum WeatherType {
		좋음, 나쁨, 보통
	}
	
	@Getter
	public enum LevelType {
		양호("#00D76A"),
		주의("#FFEB0E"),
		경계("#FF9710"),
		심각("#FF2E2F");
		
		private String color;
		
		private LevelType(String color) {
			this.color = color;
		}
	}
}
