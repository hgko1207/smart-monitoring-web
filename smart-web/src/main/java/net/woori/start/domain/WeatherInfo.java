package net.woori.start.domain;

import lombok.Data;
import net.woori.start.domain.EnumType.WeatherType;

@Data
public class WeatherInfo {

	private String date;
	
	/** 온도 */
	private float temp;
	
	/** 날씨 타입 : 맑음, 흐림 등 */
	private String type;
	
	/** 강우량 / 강수확률 */
	private int rainfall;
	
	/** 미세먼지 */
	private WeatherType fineDust;
	
	/** 오존 */
	private WeatherType ozone;
	
	/** 날씨 설명 */
	private String description;
}
