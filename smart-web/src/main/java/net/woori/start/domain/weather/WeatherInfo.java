package net.woori.start.domain.weather;

import lombok.Data;
import net.woori.start.domain.EnumType.Status;

@Data
public class WeatherInfo {
	
	private String date;
	
	private String hour;
	
	/** 이미지 타입 */
	private String icon;
	
	/** 온도 */
	private float temp;
	
	/** 습도 */
	private float hum;
	
	/** 날씨 타입 : 맑음, 흐림 등 */
	private String type;
	
	/** 강수확률/강수량 */
	private float rainfall;
	private float rain;
	
	/** 풍향 */
	private String windDirection;
	
	/** 풍속 */
	private float windSpeed;
	
	/** 미세먼지 */
	private Status fineDust;
	
	/** 오존 */
	private Status ozone;
	
	/** 날씨 설명 */
	private String description;
}
