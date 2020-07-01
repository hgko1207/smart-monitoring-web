package net.woori.start.domain;

import lombok.Data;
import net.woori.start.domain.EnumType.LevelType;

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
	private LevelType totalLevel; //양호, 주의, 경계, 심각
	
	private LevelType level1;
	
	private LevelType level2;
	
	private LevelType level3;
}
