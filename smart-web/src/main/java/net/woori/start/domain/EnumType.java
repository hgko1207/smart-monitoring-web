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
	
	@Getter
	public enum LevelType {
		양호(0),
		주의(1),
		경계(2),
		심각(3);
		
		private int id;
		
		private LevelType(int id) {
			this.id = id;
		}
	}
	
	public enum LocationType {
		상층, 중층, 하층;
	}
	
	public enum ChartType {
		일별, 주별, 월별;
	}
}
