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
		토양수분("%", "수분"),
		토양온도("°C", "온도");
		
		private String unit;
		
		private String name;
		
		private SensorType(String unit, String name) {
			this.unit = unit;
			this.name = name;
		}
	}
	
	@Getter
	public enum WeatherType {
		기온, 습도, 풍향, 평균풍속, 최대풍속, 강수량, 일조시간, 토양수분;
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
	
	public enum Status {
		좋음, 나쁨, 보통
	}
	
	@Getter
	public enum LevelType {
		양호("#00D76A", "양호단계"),
		주의("#FFEB0E", "주의단계"),
		경계("#FF9710", "경계단계"),
		심각("#FF2E2F", "심각단계");
		
		private String color;
		
		private String name;
		
		private LevelType(String color, String name) {
			this.color = color;
			this.name = name;
		}
	}
}
