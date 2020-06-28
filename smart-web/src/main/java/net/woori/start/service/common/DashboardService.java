package net.woori.start.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.EnumType.LevelType;
import net.woori.start.domain.EnumType.PointType;
import net.woori.start.domain.EnumType.WeatherType;
import net.woori.start.domain.EnvironmentInfo;
import net.woori.start.domain.WeatherInfo;
import net.woori.start.domain.db.PointInfo;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.MeasurementService;
import net.woori.start.service.PointInfoService;

/**
 * 대시보드 데이터 서비스
 * 
 * @author hgko
 *
 */
@Service
public class DashboardService {
	
	@Autowired
	private PointInfoService pointInfoService;
	
	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private DateService dateService;
	
	@Autowired
	private MapService mapService;
	
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
		weatherInfo.setDate(dateService.parseDate(param.getCurrentDate()) + " 기준");
		weatherInfo.setTemp(22);
		weatherInfo.setType("맑음");
		weatherInfo.setRainfall(0);
		weatherInfo.setFineDust(WeatherType.보통);
		weatherInfo.setOzone(WeatherType.좋음);
		weatherInfo.setDescription("어제 기온와 같음");
		
		return weatherInfo;
	}
	
	private EnvironmentInfo createEnvironmentInfo(SearchParam param) {
		EnvironmentInfo info = new EnvironmentInfo();
		info.setPoint("A " + param.getSensorPoint().name());
		info.setSensor(param.getSensor().name());
		info.setDate(dateService.parseDate(param.getCurrentDate()) + " 기준");
		info.setCurrent(dateService.parseDay(param.getCurrentDate()) + " 현재");
		info.setLevel(LevelType.양호.getName());
		info.setLevel1(LevelType.양호.getName());
		info.setLevel2(LevelType.양호.getName());
		info.setLevel3(LevelType.양호.getName());
		
		return info;
	}

	/**
	 * 대시보드 Bar 차트 생성
	 * @param param
	 * @return
	 */
	public DashboardInfo createBarChartInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		
		PointInfo pointA = pointInfoService.get(PointType.A.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementService.getDailyList(pointA.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilABarChart(chartService.createBarChart(PointType.A, param, data));
			});
		}
		
		PointInfo pointB = pointInfoService.get(PointType.B.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementService.getDailyList(pointB.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilBBarChart(chartService.createBarChart(PointType.B, param, data));
			});
		}
		
		PointInfo pointC = pointInfoService.get(PointType.C.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementService.getDailyList(pointC.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilCBarChart(chartService.createBarChart(PointType.C, param, data));
			});
		}
		
		PointInfo pointD = pointInfoService.get(PointType.D.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementService.getDailyList(pointD.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilDBarChart(chartService.createBarChart(PointType.D, param, data));
			});
		}
		
		PointInfo pointE = pointInfoService.get(PointType.E.getName() + " " + param.getSensorPoint().name());
		if (pointA != null) {
			measurementService.getDailyList(pointE.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
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
