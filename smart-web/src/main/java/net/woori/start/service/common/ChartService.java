package net.woori.start.service.common;

import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.chart.BarChartSeries;
import net.woori.start.domain.chart.ChartInfo;
import net.woori.start.domain.chart.LineChartSeries;

/**
 * 대쉬보드 차트
 * 
 * @author hgko
 *
 */
@Service
public class ChartService {

	public DashboardInfo createChartInfo() {
		DashboardInfo dashboardInfo = new DashboardInfo();
		createBarChart(dashboardInfo);
		createLineChart(dashboardInfo);
		return dashboardInfo;
	}
	
	public void createBarChart(DashboardInfo dashboardInfo) {
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
	}
	
	public void createLineChart(DashboardInfo dashboardInfo) {
		ChartInfo tempAChartInfo = new ChartInfo("토양온도");
		ChartInfo waterAChartInfo = new ChartInfo("토양수분");
		
		String[] categories = {"2020-05-01", "2020-05-02", "2020-05-03", "2020-05-04", "2020-05-05", "2020-05-06", "2020-05-07"};
		for (String data : categories) {
			tempAChartInfo.addCategory(data);
			waterAChartInfo.addCategory(data);
		}
		
		LineChartSeries tempAChartSeries = new LineChartSeries("토양온도");
		tempAChartSeries.addDataItem(14);
		tempAChartSeries.addDataItem(18);
		tempAChartSeries.addDataItem(20);
		tempAChartSeries.addDataItem(16);
		tempAChartSeries.addDataItem(12);
		tempAChartSeries.addDataItem(17);
		tempAChartSeries.addDataItem(15);
		
		LineChartSeries waterAChartSeries = new LineChartSeries("토양수분");
		waterAChartSeries.addDataItem(60);
		waterAChartSeries.addDataItem(70);
		waterAChartSeries.addDataItem(75);
		waterAChartSeries.addDataItem(72);
		waterAChartSeries.addDataItem(54);
		waterAChartSeries.addDataItem(86);
		waterAChartSeries.addDataItem(65);
		
		tempAChartInfo.addListChartSeries(tempAChartSeries);
		waterAChartInfo.addListChartSeries(waterAChartSeries);
		
		dashboardInfo.setTempALineChart(tempAChartInfo);
		dashboardInfo.setWaterALineChart(waterAChartInfo);
	}
}
