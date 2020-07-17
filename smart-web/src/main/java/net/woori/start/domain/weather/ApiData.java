package net.woori.start.domain.weather;

import lombok.Data;
import lombok.Getter;

@Data
public class ApiData {

	private ResponseJson response;
	
	/**
	 * 코드값 정보
	 */
	@Getter
	public enum CategoryType {
		POP("강수확률", "강수확률"),
		PTY("강수형태", "코드값"),
		R06("6시간 강수량", "범주 (1 mm)"),
		REH("습도", "%"),
		S06("6시간 신적설", "범주 (1 cm)"),
		SKY("하늘상태", "코드값"),
		T3H("3시간 기온", "℃"),
		TMN("아침 최저기온", "℃"),
		TMX("낮 최저기온", "℃"),
		UUU("동서바람성분", "m/s"),
		VVV("남북바람성분", "m/s"),
		WAV("파고", "M"),
		VEC("풍향", "m/s"),
		WSD("풍속", "1");
		
		private String name;
		
		private String unit;
		
		private CategoryType(String name, String unit) {
			this.name = name;
			this.unit = unit;
		}
	}
	
	/**
	 * 하늘상태 코드 타입
	 */
	@Getter
	public enum SkyType {
		맑음(1, "01"),
		구름조금(2, "02"),
		구름많음(3, "03"),
		흐림(4, "04");
		
		private int num;
		
		private String icon;
		
		private SkyType(int num, String icon) {
			this.num = num;
			this.icon = icon;
		}
		
		public static SkyType value(float value) {
			for (SkyType type : SkyType.values()) {
				if (type.getNum() == value) {
					return type;
				}
			}
			return null;
		}
	}
	
	/**
	 * 강수상태 코드 타입
	 */
	@Getter
	public enum PtyType {
		없음(0, ""),
		비(1, "05"),
		진눈개비(2, "07"),
		눈(3, "08"),
		소나기(4, "09");
		
		private int num;
		
		private String icon;
		
		private PtyType(int num, String icon) {
			this.num = num;
			this.icon = icon;
		}
		
		public static PtyType value(float value) {
			for (PtyType type : PtyType.values()) {
				if (type.getNum() == value) {
					return type;
				}
			}
			return null;
		}
	}
	
	@Getter
	public enum WindType {
		N0(0, "북"),
		NNE(1, "북북동"),
		NE(2, "북동"),
		ENE(3, "동북동"),
		E(4, "동"),
		ESE(5, "동남동"),
		SE(6, "남동"),
		SSE(7, "남남동"),
		S(8, "남"),
		SSW(9, "남남서"),
		SW(10, "남서"),
		WSW(11, "서남서"),
		W(12, "서"),
		WNW(13, "서북서"),
		NW(14, "북서"),
		NNW(15, "북북서"),
		N16(16, "북");
		
		private int code;
		
		private String name;
		
		private WindType(int code, String name) {
			this.code = code;
			this.name = name;
		}
		
		public static WindType value(int value) {
			for (WindType type : WindType.values()) {
				if (type.getCode() == value) {
					return type;
				}
			}
			return null;
		}
	}
}
