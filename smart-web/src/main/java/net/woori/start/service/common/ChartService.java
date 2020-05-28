package net.woori.start.service.common;

import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.chart.BarChartSeries;
import net.woori.start.domain.chart.ChartInfo;

@Service
public class ChartService {

	public DashboardInfo createChartInfo() {
		DashboardInfo dashboardInfo = new DashboardInfo();
		
		ChartInfo soilAChartInfo = new ChartInfo();
		ChartInfo soilBChartInfo = new ChartInfo();
		ChartInfo soilCChartInfo = new ChartInfo();
		ChartInfo soilDChartInfo = new ChartInfo();
		ChartInfo soilEChartInfo = new ChartInfo();
		
		String[] categories = {"50cm", "30cm", "10cm"};
		for (String data : categories) {
			soilAChartInfo.addCategory(data);
			soilBChartInfo.addCategory(data);
			soilCChartInfo.addCategory(data);
			soilDChartInfo.addCategory(data);
			soilEChartInfo.addCategory(data);
		}
		
		BarChartSeries soilAChartSeries = new BarChartSeries("토양수분 A");
		soilAChartSeries.addDataItem(22.9f);
		soilAChartSeries.addDataItem(27.3f);
		soilAChartSeries.addDataItem(21.8f);
		
		BarChartSeries soilBChartSeries = new BarChartSeries("토양수분 B");
		soilBChartSeries.addDataItem(21.4f);
		soilBChartSeries.addDataItem(14.6f);
		soilBChartSeries.addDataItem(22.1f);
		
		BarChartSeries soilCChartSeries = new BarChartSeries("토양수분 C");
		soilCChartSeries.addDataItem(10.6f);
		soilCChartSeries.addDataItem(34.3f);
		soilCChartSeries.addDataItem(44.3f);
		
		BarChartSeries soilDChartSeries = new BarChartSeries("토양수분 D");
		soilDChartSeries.addDataItem(11.9f);
		soilDChartSeries.addDataItem(13.7f);
		soilDChartSeries.addDataItem(23.5f);
		
		BarChartSeries soilEChartSeries = new BarChartSeries("토양수분 E");
		soilEChartSeries.addDataItem(11.9f);
		soilEChartSeries.addDataItem(13.7f);
		soilEChartSeries.addDataItem(25.6f);
		
		soilAChartInfo.addBarChartSeries(soilAChartSeries);
		soilBChartInfo.addBarChartSeries(soilBChartSeries);
		soilCChartInfo.addBarChartSeries(soilCChartSeries);
		soilDChartInfo.addBarChartSeries(soilDChartSeries);
		soilEChartInfo.addBarChartSeries(soilEChartSeries);
		
		dashboardInfo.setSoilABarChart(soilAChartInfo);
		dashboardInfo.setSoilBBarChart(soilBChartInfo);
		dashboardInfo.setSoilCBarChart(soilCChartInfo);
		dashboardInfo.setSoilDBarChart(soilDChartInfo);
		dashboardInfo.setSoilEBarChart(soilEChartInfo);
		
		return dashboardInfo;
	}
}
