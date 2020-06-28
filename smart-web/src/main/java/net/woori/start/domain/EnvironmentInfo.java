package net.woori.start.domain;

import lombok.Data;

/**
 * 생육환경 정보
 * 
 * @author hgko
 *
 */
@Data
public class EnvironmentInfo {

	private String point;
	
	private String sensor;
	
	/** 기준 일시 */
	private String date;
	
	private String current;
	
	/** 지점 단계 */
	private String level;
	
	private String level1;
	
	private String level2;
	
	private String level3;
}
