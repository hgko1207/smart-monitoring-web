package net.woori.start.service.common;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.woori.start.domain.weather.OpenWeather;
import net.woori.start.domain.weather.OpenWeather.Weather;
import net.woori.start.domain.weather.WeatherInfo;
import net.woori.start.domain.weather.ApiData.WindType;

/**
 * OpenWeather API 사용
 * https://openweathermap.org/
 * 
 * @author hgko
 *
 */
@Service
public class OpenWeatherService {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat hourFormat = new SimpleDateFormat("HH");

	private final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
	private final String serviceKey = "6ede1c29aec89ddbb54f03f4c2f29a3b";
	
	@PostConstruct
	public WeatherInfo createWeatherInfo() {
		WeatherInfo weatherInfo = new WeatherInfo();
		getWeather(weatherInfo);
		return weatherInfo;
	}
	
	public void getWeather(WeatherInfo weatherInfo) {
		StringBuilder urlBuilder = new StringBuilder(BASE_URL);
		try {
			urlBuilder.append("?" + URLEncoder.encode("q", "UTF-8") + "=Koesan");
			urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + serviceKey);
			urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=kr");
			urlBuilder.append("&" + URLEncoder.encode("units", "UTF-8") + "=metric");
			
	        RestTemplate restTemplate = new RestTemplate();
	        OpenWeather response = restTemplate.getForObject(urlBuilder.toString(), OpenWeather.class);
	        
	        for (Weather weather : response.getWeather()) {
	        	weatherInfo.setType(weather.getDescription());
	        	weatherInfo.setIcon(weather.getIcon());
	        }
	        
	        weatherInfo.setTemp(response.getMain().getTemp());
	        weatherInfo.setHum(response.getMain().getHumidity());
	        weatherInfo.setWindSpeed(response.getWind().getSpeed());
	        weatherInfo.setWindDirection(getWindDirection(response.getWind().getDeg()));
	        
	        if (response.getRain() != null) {
	        	weatherInfo.setRain(response.getRain().getRain1h());
	        }
	        
	        long time = Long.parseLong(response.getDt() + "000");
	        Date timeInDate = new Date(time); 
	        weatherInfo.setDate(dateFormat.format(timeInDate));
	        weatherInfo.setHour(hourFormat.format(timeInDate));
	        
	        System.err.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getWindDirection(int degree) {
		int result = (int)((degree + 22.5 * 0.5) / 22.5);
		WindType windType = WindType.value(result);
		return windType.getName();
	}
}
