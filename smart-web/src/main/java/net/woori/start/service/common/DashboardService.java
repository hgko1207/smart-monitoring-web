package net.woori.start.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.EnumType.PointType;
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
	
	public DashboardInfo createDashboardInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		return dashboardInfo;
	}

	public DashboardInfo createBarChartInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		
//		String startDate = "2020-06-22 00:00:00";
//		String endDate = "2020-06-22 23:59:59";
		
		PointInfo pointA = pointInfoService.get(PointType.A.getName());
		if (pointA != null) {
			measurementService.getDailyList(pointA.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilABarChart(chartService.createBarChart(PointType.A, param, data));
			});
		}
		
		PointInfo pointB = pointInfoService.get(PointType.B.getName());
		if (pointA != null) {
			measurementService.getDailyList(pointB.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilBBarChart(chartService.createBarChart(PointType.B, param, data));
			});
		}
		
		PointInfo pointC = pointInfoService.get(PointType.C.getName());
		if (pointA != null) {
			measurementService.getDailyList(pointC.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilCBarChart(chartService.createBarChart(PointType.C, param, data));
			});
		}
		
		PointInfo pointD = pointInfoService.get(PointType.D.getName());
		if (pointA != null) {
			measurementService.getDailyList(pointD.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilDBarChart(chartService.createBarChart(PointType.D, param, data));
			});
		}
		
		PointInfo pointE = pointInfoService.get(PointType.E.getName());
		if (pointA != null) {
			measurementService.getDailyList(pointE.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				dashboardInfo.setSoilEBarChart(chartService.createBarChart(PointType.E, param, data));
			});
		}
		
//		chartService.createBarChart(dashboardInfo, param);
		
		return dashboardInfo;
	}

	public DashboardInfo createLineChartInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		
		PointInfo pointInfo = pointInfoService.get(param.getPointType().getName());
		if (pointInfo != null) {
			chartService.createLineChart(param.getPointType(), pointInfo, param, dashboardInfo);
		}
		
		return dashboardInfo;
	}
	
}
