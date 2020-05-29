package net.woori.start.service.common;

import org.springframework.stereotype.Service;

import net.woori.start.domain.chart.ChartInfo;
import net.woori.start.domain.param.SearchParam;

@Service
public class MeasurementService {

	public ChartInfo createChartInfo(SearchParam param) {
		ChartInfo chartInfo = new ChartInfo();
		
		return chartInfo;
	}
}
