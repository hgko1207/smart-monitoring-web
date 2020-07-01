package net.woori.start.domain.chart;

import java.util.Date;

/**
 * 계측 정보 조회 시 차트에 필요한 데이터로 변환
 * 
 * @author hgko
 *
 */
public interface ChartData {
	
	Date getDate();
	
	float getTemp1();
	
	float getTemp2();
	
	float getTemp3();
	
	float getWater1();
	
	float getWater2();
	
	float getWater3();
	
}
