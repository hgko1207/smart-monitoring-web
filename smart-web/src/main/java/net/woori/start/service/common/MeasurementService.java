package net.woori.start.service.common;

import org.springframework.stereotype.Service;

import net.woori.start.domain.chart.ChartInfo;

@Service
public class MeasurementService {

	public ChartInfo createChartInfo() {
		ChartInfo chartInfo = new ChartInfo();
		
		return chartInfo;
	}
}
