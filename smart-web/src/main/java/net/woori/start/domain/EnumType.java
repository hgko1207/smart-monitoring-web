package net.woori.start.domain;

import lombok.Getter;

public class EnumType {
	
	@Getter
	public enum PointType {
		A("A"),
		B("B"),
		C("C"),
		D("D"),
		E("E");
		
		private String name;
		
		private PointType(String name) {
			this.name = name;
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
