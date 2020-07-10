package net.woori.start.domain.weather;

import lombok.Data;
import net.woori.start.domain.EnumType.Status;

@Data
public class WeatherInfo {
	
	private String date;
	
	private String hour;
	
	/** 온도 */
	private float temp;
	
	/** 날씨 타입 : 맑음, 흐림 등 */
	private String type;
	
	/** 강우량 / 강수확률 */
	private float rainfall;
	
	/** 미세먼지 */
	private Status fineDust;
	
	/** 오존 */
	private Status ozone;
	
	/** 날씨 설명 */
	private String description;
}
