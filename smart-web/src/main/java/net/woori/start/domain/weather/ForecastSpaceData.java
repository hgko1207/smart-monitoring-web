package net.woori.start.domain.weather;

import lombok.Data;
import net.woori.start.domain.weather.ApiData.CategoryType;

/**
 * 동네예보 정보 도메인
 * 
 * @author hgko
 *
 */
@Data
public class ForecastSpaceData {

	/** 발표일자 */
	private String baseDate;
	
	/** 발표시각 */
	private String baseTime;
	
	/** 예보일자 */
	private String fcstDate;
	
	/** 예보시각 */
	private String fcstTime;
	
	/** 자료구분문자 */
	private CategoryType category;
	
	/** 예보 값 */
	private float fcstValue;
	
	/** 예보지점 X 좌표 */
	private float nx;
	
	/** 예보지점 Y 좌표 */
	private float ny;
}
