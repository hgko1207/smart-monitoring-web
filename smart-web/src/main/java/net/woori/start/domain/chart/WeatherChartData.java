package net.woori.start.domain.chart;

import java.util.Date;

/**
 * 기상 정보 조회 시 차트에 필요한 데이터로 변환
 * 
 * @author hgko
 *
 */
public interface WeatherChartData {
	
	Date getDate();
	
	float getTemp();
	
	float getHum();
	
	float getAfp();
	
	float getArvlty();
	
	float getSoilMitr();
	
	float getSolradQy();
}
