package net.woori.start.service.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.woori.start.domain.weather.ApiData;
import net.woori.start.domain.weather.ApiData.CategoryType;
import net.woori.start.domain.weather.ApiData.PtyType;
import net.woori.start.domain.weather.ApiData.SkyType;
import net.woori.start.domain.weather.ApiData.WindType;
import net.woori.start.domain.weather.ForecastSpaceData;
import net.woori.start.domain.weather.ResponseJson.Items;
import net.woori.start.domain.weather.WeatherInfo;

@Service
public class AwsService {
	
	/** 초단기예보 URL */
	private final String BASE_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastTimeData";
	/** 동네예보조회 URL */
//	private final String BASE_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";
	private final String serviceKey = "MaaMRUGv3%2BKhRIQwrofI8uDLbm0O%2BO9lTDfgV6ruU%2FGp%2B73vwoBEdlJXNPmYy4VbUXxoiLh2%2FtSs%2Fah05Ww1rw%3D%3D";

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
	private final SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
	
	private String date;
	private String searchDate;
	private String searchHour;
	
	public WeatherInfo createWeatherInfo() {
		WeatherInfo weatherInfo = new WeatherInfo();
		getForecastTimeData(weatherInfo);
		return weatherInfo;
	}
	
	/**
	 * URL 생성
	 * 괴산군 불정면(nx: 75, ny: 112)
	 * @param url
	 * @param date
	 * @param hour
	 * @return
	 */
	private String createUrl(String url) {
		StringBuilder urlBuilder = new StringBuilder(url);
		try {
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
			urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(searchDate, "UTF-8")); /*현재일*/
			urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(searchHour, "UTF-8")); /*현재시간*/
			urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(75 + "", "UTF-8")); /*지점 X 좌표값*/
			urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(112 + "", "UTF-8")); /*지점의 Y 좌표값*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return urlBuilder.toString();
	}
	
	/**
	 * 초단기예보 조회
	 * @param weatherInfo
	 */
	public void getForecastTimeData(WeatherInfo weatherInfo) {
		getForecastTime();
		
		try {
			URL url = new URL(createUrl(BASE_URL));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        
	        System.out.println("Response code: " + conn.getResponseCode());
	        
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        	StringBuilder sb = new StringBuilder();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				in.lines().forEach(line -> {
					sb.append(line);
				});
				
				in.close();
				conn.disconnect();
				
				createWeatherInfo(weatherInfo, sb.toString());
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 동네예보 조회
	 * @param param 
	 */
	public void getForecastSpaceData(WeatherInfo weatherInfo) {
		
		getCurrentDateTime();
		
		try {
			URL url = new URL(createUrl(BASE_URL));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        
	        System.out.println("Response code: " + conn.getResponseCode());
	        
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        	StringBuilder sb = new StringBuilder();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				in.lines().forEach(line -> {
					sb.append(line);
				});
				
				in.close();
				conn.disconnect();
				
				createWeatherInfo(weatherInfo, sb.toString());
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createWeatherInfo(WeatherInfo weatherInfo, String json) {
		SkyType skyType = null;
		PtyType ptyType = null;
		
		String hour = hourFormat.format(new Date()) + "00";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ApiData data = objectMapper.readValue(json, ApiData.class);
			
			Items items = data.getResponse().getBody().getItems();
			for (ForecastSpaceData item : items.getItem()) {
				if (hour.equals(item.getFcstTime())) {
					if (item.getCategory() == CategoryType.T1H) {
						weatherInfo.setTemp(item.getFcstValue());
					} else if (item.getCategory() == CategoryType.REH) {
						weatherInfo.setHum(item.getFcstValue());
					} else if (item.getCategory() == CategoryType.POP) {
						weatherInfo.setRainfall(item.getFcstValue());
					}  else if (item.getCategory() == CategoryType.RN1) {
						weatherInfo.setRain(item.getFcstValue());
					} else if (item.getCategory() == CategoryType.VEC) {
						weatherInfo.setWindDirection(getWindDirection((int)item.getFcstValue()));
					}  else if (item.getCategory() == CategoryType.WSD) {
						weatherInfo.setWindSpeed(item.getFcstValue());
					} else if (item.getCategory() == CategoryType.SKY) {
						skyType = SkyType.value(item.getFcstValue());
					} else if (item.getCategory() == CategoryType.PTY) {
						ptyType = PtyType.value(item.getFcstValue());
					}
				}
			}
			
			if (ptyType == PtyType.없음) {
				weatherInfo.setType(skyType.name());
				weatherInfo.setIcon(skyType.getIcon());
			} else {
				weatherInfo.setType(ptyType.name());
				weatherInfo.setIcon(ptyType.getIcon());
			}
			
			weatherInfo.setHour(hour.substring(0, 2));
			weatherInfo.setDate(date + " " + hour.substring(0, 2) + ":00 기준");
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	private String getWindDirection(int value) {
		int result = (int)((value + 22.5 * 0.5) / 22.5);
		WindType windType = WindType.value(result);
		return windType.getName();
	}
	
	/**
	 * 동네예보 시간 조회
	 * @return
	 */
	private void getCurrentDateTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -4);
		searchDate = dayFormat.format(calendar.getTime());
		date = dateFormat.format(calendar.getTime());

		int hour = Integer.parseInt(hourFormat.format(calendar.getTime()));
		if (hour >= 23) {
			searchHour = "2300";
		} else if (hour >= 20) {
			searchHour = "2000";
		} else if (hour >= 17) {
			searchHour = "1700";
		} else if (hour >= 14) {
			searchHour = "1400";
		} else if (hour >= 11) {
			searchHour = "1100";
		} else if (hour >= 8) {
			searchHour = "0800";
		} else if (hour >= 5) {
			searchHour = "0500";
		} else if (hour >= 2) {
			searchHour = "0200";
		} else if (hour >= 0) {
			calendar.add(Calendar.DATE, -1);
			date = dateFormat.format(calendar.getTime());
			searchDate = dayFormat.format(calendar.getTime());
			searchHour = "2300";
		}
	}
	
	/**
	 * 초단기예보 시간 조회
	 * @return
	 */
	public void getForecastTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -1);
		
		date = dateFormat.format(calendar.getTime());
		searchDate = dayFormat.format(calendar.getTime());
		searchHour =  hourFormat.format(calendar.getTime()) + "30";
	}
}
