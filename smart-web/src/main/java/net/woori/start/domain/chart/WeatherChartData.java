package net.woori.start.domain.chart;

import java.util.Date;

public interface WeatherChartData {
	
	Date getDate();
	
	float getTemp();
	
	float getHum();
	
	float getAfp();
	
	float getArvlty();
	
	float getSoilMitr();
	
	float getSolradQy();
}
