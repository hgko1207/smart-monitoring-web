package net.woori.start.domain.param;

import lombok.Data;

@Data
public class SearchParam {

	/** 지점 */
	private String point;
	
	/** 위치 */
	private String location;
	
	/** 그래프 항목 - 토양온도, 토양수분 */
	private String sensor;
	
	/** 그래프 항목 - 일별, 월별 */
	private String type;
	
	private String startDate;
	
	private String endDate;
}
