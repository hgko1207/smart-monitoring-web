package net.woori.start.service.common;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.EnumType.LevelType;
import net.woori.start.domain.EnumType.PointType;
import net.woori.start.domain.EnumType.Status;
import net.woori.start.domain.EnvironmentInfo;
import net.woori.start.domain.db.PointInfo;
import net.woori.start.domain.db.Weather;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.domain.weather.WeatherInfo;
import net.woori.start.service.MeasurementLogService;
import net.woori.start.service.PointInfoService;
import net.woori.start.service.WeatherService;

/**
 * 대시보드 데이터 서비스
 * 
 * @author hgko
 *
 */
@Service
public class DashboardService {
	
	private final SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Autowired
	private PointInfoService pointInfoService;
	
	@Autowired
	private MeasurementLogService measurementLogService;
	
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private DateService dateService;
	
	@Autowired
	private MapService mapService;
	
	@Autowired
	private WeatherService weatherService;
	
	/**
	 * 대시보드 날씨 정보 생성
	 * @param param
	 * @return
	 */
	public DashboardInfo createDashboardInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setWeatherInfo(createWeatherInfo(param));
		dashboardInfo.setEnvironmentInfo(createEnvironmentInfo(param));
		dashboardInfo.setMapInfo(mapService.createMapInfo(param));
		
		return dashboardInfo;
	}
	
	private WeatherInfo createWeatherInfo(SearchParam param) {
		WeatherInfo weatherInfo = new WeatherInfo();
		
		Weather weather = weatherService.getRecentData();
		if (weather != null) {
			weatherInfo.setDate(hourFormat.format(weather.getMeasDt()) + " 기준");
			weatherInfo.setTemp(weather.getTemp_150());
			weatherInfo.setType("맑음");
			weatherInfo.setRainfall(0);
			weatherInfo.setFineDust(Status.보통);
			weatherInfo.setOzone(Status.좋음);
			weatherInfo.setDescription("어제 기온와 같음");
		}
		
		return weatherInfo;
	}
	
	private EnvironmentInfo createEnvironmentInfo(SearchParam param) {
		Map<String, EnvironmentInfo> infoMap = new HashMap<>();
		
//		pointInfoService.getList(param.getSensorPoint()).forEach(pointInfo -> {
////			System.err.println(pointInfo);
//			
//			EnvironmentInfo info = new EnvironmentInfo();
//			
//			int totalLevel = 0; // 0 양호, 1 주의, 2 경계, 3 심각
//			int level = 0; // 0 양호, 1 주의, 2 경계, 3 심각
//			
//			for (Measurement data : measurementService.getList(pointInfo.getPointSq(), param.getStartDate(), param.getCurrentDate())) {
//				if (totalLevel < level) {
//					totalLevel = level;
//				}
//				
//				LevelType levelType1 = null;
//				LevelType levelType2 = null;
//				LevelType levelType3 = null;
//				
//				if (param.getSensor() == SensorType.토양수분) {
//					levelType1 = getSoilWaterLevel(data.getVwcCh1());
//					levelType2 = getSoilWaterLevel(data.getVwcCh2());
//					levelType3 = getSoilWaterLevel(data.getVwcCh3());
//					
//					
//				} else if (param.getSensor() == SensorType.토양온도) {
//					levelType1 = getSoilTempLevel(data.getTempCh1());
//					levelType2 = getSoilTempLevel(data.getTempCh2());
//					levelType3 = getSoilTempLevel(data.getTempCh3());
//				}
//				
//				info.setLevel1(levelType1);
//				info.setLevel2(levelType2);
//				info.setLevel3(levelType3);
//				level = levelType1.getLevel();
//				level = levelType2.getLevel();
//				level = levelType3.getLevel();
//			}
//			
//			if (totalLevel == 0) {
//				info.setTotalLevel(LevelType.양호);
//			} else if (totalLevel == 1) {
//				info.setTotalLevel(LevelType.주의);
//			} else if (totalLevel == 2) {
//				info.setTotalLevel(LevelType.경계);
//			} else if (totalLevel == 3) {
//				info.setTotalLevel(LevelType.심각);
//			}
//			
//			infoMap.put(pointInfo.getPointNm(), info);
//		});
		
		EnvironmentInfo info = new EnvironmentInfo();
		info.setPoint("A " + param.getSensorPoint().name());
		info.setSensor(param.getSensor().name());
		
		info.setDate(dateService.parseDate(param.getCurrentDate()) + " 기준");
		info.setCurrent(dateService.parseDay(param.getCurrentDate()) + " 현재");
		
		info.setTotalLevel(LevelType.양호);
		info.setLevel1(LevelType.양호);
		info.setLevel2(LevelType.양호);
		info.setLevel3(LevelType.양호);
		
		return info;
	}
	
	private LevelType getSoilWaterLevel(float value) {
		if (value >= 0 && value < 4.6) {
			return LevelType.주의;
		} else if (value >= 4.6 && value < 44) {
			return LevelType.양호;
		} else if (value >= 44) {
			return LevelType.경계;
		}
		return LevelType.심각;
	}
	
	private LevelType getSoilTempLevel(float value) {
		if ((value >= 0 && value < 10) || (value >= 35 && value <= 45)) {
			return LevelType.심각;
		} else if ((value >= 10 && value < 15) || (value >= 28 && value < 35)) {
			return LevelType.경계;
		} else if ((value >= 15 && value < 19) || (value >= 25 && value < 28)) {
			return LevelType.주의;
		} else if (value >= 19 && value < 25) {
			return LevelType.양호;
		} else {
			return LevelType.심각;
		}
	}

	/**
	 * 대시보드 Bar 차트 생성
	 * @param param
	 * @return
	 */
	public DashboardInfo createBarChartInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setSensor(param.getSensor().name());
		dashboardInfo.setDateTime(dateService.parseDate(param.getCurrentDate()));
		
		PointInfo pointA = pointInfoService.get(PointType.A.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementLogService.getDailyList(pointA.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilABarChart(chartService.createBarChart(PointType.A, param, data));
			});
		}
		
		PointInfo pointB = pointInfoService.get(PointType.B.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementLogService.getDailyList(pointB.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilBBarChart(chartService.createBarChart(PointType.B, param, data));
			});
		}
		
		PointInfo pointC = pointInfoService.get(PointType.C.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementLogService.getDailyList(pointC.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilCBarChart(chartService.createBarChart(PointType.C, param, data));
			});
		}
		
		PointInfo pointD = pointInfoService.get(PointType.D.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementLogService.getDailyList(pointD.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilDBarChart(chartService.createBarChart(PointType.D, param, data));
			});
		}
		
		PointInfo pointE = pointInfoService.get(PointType.E.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementLogService.getDailyList(pointE.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilEBarChart(chartService.createBarChart(PointType.E, param, data));
			});
		}
		
		return dashboardInfo;
	}
	
	/**
	 * 대시보드 Line 차트 생성
	 * @param param
	 * @return
	 */
	public DashboardInfo createLineChartInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		
		PointInfo pointInfo = pointInfoService.get(param.getPointType().getName() + " " + param.getSensorPoint().name());
		if (pointInfo != null) {
			chartService.createLineChart(param.getPointType(), pointInfo, param, dashboardInfo);
		}
		
		return dashboardInfo;
	}
	
}
