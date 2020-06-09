package net.woori.start.domain.param;

import lombok.Data;
import net.woori.start.domain.EnumType.ChartType;
import net.woori.start.domain.EnumType.LocationType;
import net.woori.start.domain.EnumType.SensorType;

@Data
public class SearchParam {

	/** 지점 */
	private String point;
	
	/** 위치 */
	private LocationType location;
	
	/** 그래프 항목 - 토양온도, 토양수분 */
	private SensorType sensor;
	
	/** 그래프 항목 - 일별, 월별 */
	private ChartType type;
	
	private String startDate;
	
	private String endDate;
}
