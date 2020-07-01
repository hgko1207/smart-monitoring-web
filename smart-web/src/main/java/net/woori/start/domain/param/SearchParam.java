package net.woori.start.domain.param;

import lombok.Data;
import net.woori.start.domain.EnumType.ChartType;
import net.woori.start.domain.EnumType.LocationType;
import net.woori.start.domain.EnumType.PointType;
import net.woori.start.domain.EnumType.SensorPointType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.EnumType.WeatherType;

/**
 * 검색 조건 도메인
 * 
 * @author hgko
 *
 */
@Data
public class SearchParam {
	
	private PointType pointType;

	/** 지점 */
	private String point;
	
	private SensorPointType sensorPoint;
	
	/** 기상 타입 */
	private WeatherType weatherType;
	
	/** 위치 */
	private LocationType location;
	
	/** 그래프 항목 - 토양온도, 토양수분 */
	private SensorType sensor;
	
	/** 그래프 항목 - 일별, 월별 */
	private ChartType type;
	
	private String startDate;
	
	private String endDate;
	
	/** 현재시간 기준 */
	private String currentDate;
	
	/** 10일전 일시 */
	private String daysDate;
}
