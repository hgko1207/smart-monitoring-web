package net.woori.start.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.param.SearchParam;

/**
 * 대시보드 데이터 서비스
 * 
 * @author hgko
 *
 */
@Service
public class DashboardService {
	
	@Autowired
	private ChartService chartService;

	public DashboardInfo createChartInfo(SearchParam param) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		chartService.createBarChart(dashboardInfo);
		chartService.createLineChart(dashboardInfo);
		
		return dashboardInfo;
	}
}
