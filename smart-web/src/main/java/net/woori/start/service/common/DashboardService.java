package net.woori.start.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.param.SearchParam;

@Service
public class DashboardService {
	
	@Autowired
	private ChartService chartService;

	public DashboardInfo createChartInfo(SearchParam param) {
		System.err.println(param);
		
		DashboardInfo dashboardInfo = new DashboardInfo();
		chartService.createBarChart(dashboardInfo);
		chartService.createLineChart(dashboardInfo);
		
		return dashboardInfo;
	}
}
