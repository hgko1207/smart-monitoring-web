package net.woori.start.service.common;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.EnumType.LevelType;
import net.woori.start.domain.EnumType.PointType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.EnumType.Status;
import net.woori.start.domain.EnvironmentInfo;
import net.woori.start.domain.db.MeasurementLog;
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
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:00");
	
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
	 * 대시보드 정보 생성
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
	
	/**
	 * 기상 정보 생성
	 * @param param
	 * @return
	 */
	private WeatherInfo createWeatherInfo(SearchParam param) {
		WeatherInfo weatherInfo = new WeatherInfo();
		
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH시");
		
		Weather weather = weatherService.getRecentData();
		if (weather != null) {
			weatherInfo.setDate(dateFormat.format(weather.getMeasDt()) + " 기준");
			weatherInfo.setHour(hourFormat.format(weather.getMeasDt()) + " 현재");
			weatherInfo.setTemp(weather.getTemp_150());
			weatherInfo.setType("맑음");
			weatherInfo.setRainfall(0);
			weatherInfo.setFineDust(Status.보통);
			weatherInfo.setOzone(Status.좋음);
			weatherInfo.setDescription("어제 기온와 같음");
		}
		
		return weatherInfo;
	}
	
	/**
	 * 대시보드 Bar 차트 생성
	 * @param param
	 * @return
	 */
	public DashboardInfo createBarChartInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setSensor(param.getSensor().name());
		dashboardInfo.setDateTime(param.getCurrentDate() + " " + hourFormat.format(new Date()));
		
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
	
	/**
	 * 작물생육환경 정보 생성
	 * @param param
	 * @return
	 */
	private EnvironmentInfo createEnvironmentInfo(SearchParam param) {
		Map<PointType, EnvironmentInfo> infoMap = new HashMap<>();
		
		String currentDate = param.getCurrentDate() + " " + hourFormat.format(new Date());
		
		pointInfoService.getList(param.getSensorPoint()).forEach(pointInfo -> {
			PointType pointType = PointType.value(pointInfo.getPointNm());
			
			EnvironmentInfo info = new EnvironmentInfo();
			info.setPointType(pointType);
			info.setPoint(pointType.name() + " 지점");
			info.setSensor(param.getSensor().name());
			info.setDate(currentDate + " 기준");
			info.setCurrent(dateService.stringToDay(param.getCurrentDate()) + " 현재");
			
			int totalLevel = 0; // 0 양호, 1 주의, 2 경계, 3 심각
			int level = 0; // 0 양호, 1 주의, 2 경계, 3 심각
			
			for (MeasurementLog data : measurementLogService.getList(pointInfo.getPointSq(), param.getStartDate(), currentDate)) {
				if (totalLevel < level) {
					totalLevel = level;
				}
				
				LevelType levelType1 = null;
				LevelType levelType2 = null;
				LevelType levelType3 = null;
				
				if (param.getSensor() == SensorType.토양수분) {
					levelType1 = getSoilWaterLevel(data.getVwcCh3());
					levelType2 = getSoilWaterLevel(data.getVwcCh2());
					levelType3 = getSoilWaterLevel(data.getVwcCh1());
				} else if (param.getSensor() == SensorType.토양온도) {
					levelType1 = getSoilTempLevel(data.getTempCh3());
					levelType2 = getSoilTempLevel(data.getTempCh2());
					levelType3 = getSoilTempLevel(data.getTempCh1());
				}
				
				info.setLevel1(levelType1);
				info.setLevel2(levelType2);
				info.setLevel3(levelType3);
				
				if (level < levelType1.getLevel()) {
					level = levelType1.getLevel();
				}
				
				if (level < levelType2.getLevel()) {
					level = levelType2.getLevel();
				}
				
				if (level < levelType3.getLevel()) {
					level = levelType3.getLevel();
				}
			}
			
			if (totalLevel == 0) {
				info.setTotalLevel(LevelType.양호);
			} else if (totalLevel == 1) {
				info.setTotalLevel(LevelType.주의);
			} else if (totalLevel == 2) {
				info.setTotalLevel(LevelType.경계);
			} else if (totalLevel == 3) {
				info.setTotalLevel(LevelType.심각);
			}
			
			info.setLevel1Day(5);
			info.setLevel2Day(5);
			info.setLevel3Day(5);
			
			infoMap.put(pointType, info);
		});
		
		EnvironmentInfo info = infoMap.get(PointType.A);
		for (EnvironmentInfo data : infoMap.values().stream().filter(value -> value.getPointType() != PointType.A)
				.sorted(Comparator.comparing(EnvironmentInfo::getPointType)).collect(Collectors.toList())) {
			if (data.getTotalLevel().getLevel() > info.getTotalLevel().getLevel()) {
				info = data;
			}
		}
		
		return info;
	}
	
	private LevelType getSoilWaterLevel(float value) {
		if (value == 0) {
			return LevelType.양호;
		} else if (value > 0 && value < 4.6) {
			return LevelType.주의;
		} else if (value >= 4.6 && value < 44) {
			return LevelType.양호;
		} else if (value >= 44) {
			return LevelType.경계;
		}
		return LevelType.심각;
	}
	
	private LevelType getSoilTempLevel(float value) {
		if (value == 0) {
			return LevelType.양호;
		} else if ((value > 0 && value < 10) || (value >= 35 && value <= 45)) {
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
	
}
