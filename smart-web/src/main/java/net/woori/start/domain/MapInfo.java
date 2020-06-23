package net.woori.start.domain;

import lombok.Data;

@Data
public class MapInfo {

	private String point;
	
	private double latitude;
	
	private double longitude;
	
	/** 수분 데이터_CH1(상층) 데이터 */
	private float water1;
	
	/** 수분 데이터_CH2(중층) 데이터 */
	private float water2;
	
	/** 수분 데이터_CH3(하층) 데이터 */
	private float water3;
	
	/** 온도_CH1(상층) 데이터 */
	private float temp1;
	
	/** 온도_CH2(중층) 데이터 */
	private float temp2;
	
	/** 온도_CH3(하층) 데이터 */
	private float temp3;
}
