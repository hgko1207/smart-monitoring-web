package net.woori.start.service.common;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.woori.start.domain.db.Weather;
import net.woori.start.domain.weather.Response;
import net.woori.start.service.WeatherService;

@Service
public class WeatherInfoService {

	private final String BASE_URL = "http://weather.rda.go.kr/openapi/realtime_openapi_xml.jsp";
	private final String userId = "sondar20";
	private final String serviceKey = "fhkkcbtdunucdfwhnnextzxlemumvq";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	@Autowired
	private WeatherService weatherService;
	
	/**
	 * 농업 기상 데이터 조회
	 * 10분 마다 실행
	 */
	@PostConstruct
//	@Scheduled(cron = "0 0/10 * * * *")
	public void getWthrDataList() {
		StringBuilder urlBuilder = new StringBuilder(BASE_URL);
		try {
			urlBuilder.append("?" + URLEncoder.encode("mberid", "UTF-8") + "=" + userId);
			urlBuilder.append("&" + URLEncoder.encode("regist_ky", "UTF-8") + "=" + serviceKey);
	        
	        RestTemplate restTemplate = new RestTemplate();
	        Response response = restTemplate.getForObject(urlBuilder.toString(), Response.class);
	        
	        Weather weather = new Weather(response);
	        
	        Weather temp = weatherService.getData();
	        if (temp != null) {
	        	String date = dateFormat.format(temp.getDate());
	        	if (!response.getInfo().getDate().equals(date)) {
	        		weatherService.regist(weather);
	        	}
	        } else {
	        	weatherService.regist(weather);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
