package net.woori.start.domain.param;

import lombok.Data;

@Data
public class SearchParam {

	/** 지점 */
	private String point;
	
	/** 위치 */
	private String location;
	
	private String startDate;
	
	private String endDate;
}
