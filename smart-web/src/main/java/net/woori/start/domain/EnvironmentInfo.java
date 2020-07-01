package net.woori.start.domain;

import lombok.Data;
import net.woori.start.domain.EnumType.LevelType;
import net.woori.start.domain.EnumType.PointType;

/**
 * 생육환경 정보
 * 
 * @author hgko
 *
 */
@Data
public class EnvironmentInfo {
	
	private PointType pointType;

	private String point;
	
	private String sensor;
	
	/** 기준 일시 */
	private String date;
	
	private String current;
	
	/** 지점 단계 */
	private LevelType totalLevel; //양호, 주의, 경계, 심각
	
	private String totalLevelColor;
	
	/** 10cm 센서 정보 */
	private LevelType level1;
	private String level1Color;
	private int level1Day;
	
	/** 30cm 센서 정보 */
	private LevelType level2;
	private String level2Color;
	private int level2Day;
	
	/** 50cm 센서 정보 */
	private LevelType level3;
	private String level3Color;
	private int level3Day;
	
	public void setTotalLevel(LevelType totalLevel) {
		this.totalLevel = totalLevel;
		this.totalLevelColor = totalLevel.getColor();
	}
	
	public void setLevel1(LevelType level1) {
		this.level1 = level1;
		this.level1Color = level1.getColor();
	}
	
	public void setLevel2(LevelType level2) {
		this.level2 = level2;
		this.level2Color = level2.getColor();
	}
	
	public void setLevel3(LevelType level3) {
		this.level3 = level3;
		this.level3Color = level3.getColor();
	}
}
