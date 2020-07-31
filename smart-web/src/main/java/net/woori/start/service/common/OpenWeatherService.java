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
	        	weatherInfo.setType(getDescEngToKor(weather.getId()));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getWindDirection(int degree) {
		int result = (int)((degree + 22.5 * 0.5) / 22.5);
		WindType windType = WindType.value(result);
		return windType.getName();
	}
	
	private String getDescEngToKor(int id) {
		int[] w_id_arr = new int[] { 201, 200, 202, 210, 211, 212, 221, 230, 231, 232, 300, 301, 302, 310, 311, 312,
				313, 314, 321, 500, 501, 502, 503, 504, 511, 520, 521, 522, 531, 600, 601, 602, 611, 612, 615, 616, 620,
				621, 622, 701, 711, 721, 731, 741, 751, 761, 762, 771, 781, 800, 801, 802, 803, 804, 900, 901, 902, 903,
				904, 905, 906, 951, 952, 953, 954, 955, 956, 957, 958, 959, 960, 961, 962 };
		String[] w_kor_arr = { "가벼운 비를 동반한 천둥구름", "비를 동반한 천둥구름", "폭우를 동반한 천둥구름", "약한 천둥구름", "천둥구름", "강한 천둥구름",
				"불규칙적 천둥구름", "약한 연무를 동반한 천둥구름", "연무를 동반한 천둥구름", "강한 안개비를 동반한 천둥구름", "가벼운 안개비", "안개비", "강한 안개비",
				"가벼운 적은비", "적은비", "강한 적은비", "소나기와 안개비", "강한 소나기와 안개비", "소나기", "악한 비", "중간 비", "강한 비", "매우 강한 비",
				"극심한 비", "우박", "약한 소나기 비", "소나기 비", "강한 소나기 비", "불규칙적 소나기 비", "가벼운 눈", "눈", "강한 눈", "진눈깨비", "소나기 진눈깨비",
				"약한 비와 눈", "비와 눈", "약한 소나기 눈", "소나기 눈", "강한 소나기 눈", "박무", "연기", "연무", "모래 먼지", "안개", "모래", "먼지", "화산재",
				"돌풍", "토네이도", "구름 한 점 없는 맑은 하늘", "약간의 구름이 낀 하늘", "드문드문 구름이 낀 하늘", "구름이 거의 없는 하늘", "구름으로 뒤덮인 흐린 하늘",
				"토네이도", "태풍", "허리케인", "한랭", "고온", "바람부는", "우박", "바람이 거의 없는", "약한 바람", "부드러운 바람", "중간 세기 바람", "신선한 바람",
				"센 바람", "돌풍에 가까운 센 바람", "돌풍", "심각한 돌풍", "폭풍", "강한 폭풍", "허리케인" };

		for (int i = 0; i < w_id_arr.length; i++) {
			if (w_id_arr[i] == id) {
				return w_kor_arr[i];
			}
		}
		return "none";
	}
}
