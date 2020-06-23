package net.woori.start.service.common;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.DashboardInfo;
import net.woori.start.domain.EnumType.LocationType;
import net.woori.start.domain.EnumType.PointType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.chart.BarChartSeries;
import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.chart.ChartInfo;
import net.woori.start.domain.chart.LineChartSeries;
import net.woori.start.domain.db.PointInfo;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.MeasurementService;

/**
 * 차트 관련 서비스
 * 
 * @author hgko
 *
 */
@Service
public class ChartService {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private MeasurementService measurementService;
	
	/**
	 * 대시보드 Bar 차트 생성
	 * @param dashboardInfo
	 */
	public ChartInfo createBarChart(PointType type, SearchParam param, ChartData chartData) {
//		if (type == PointType.A) {
//			if (param.getSensor() == SensorType.토양수분) {
//				
//			} else if (param.getSensor() == SensorType.토양온도) {
//				
//			}
//		} else if (type == PointType.B) {
//		} else if (type == PointType.C) {
//		} else if (type == PointType.D) {
//		} else if (type == PointType.E) {
//		}
		
		ChartInfo chartInfo = new ChartInfo();
		String[] categories = {"50cm", "30cm", "10cm"};
		for (String data : categories) {
			chartInfo.addCategory(data);
		}
		
		if (param.getSensor() == SensorType.토양수분) {
			BarChartSeries soilChartSeries = new BarChartSeries(type.getTitleWater());
			soilChartSeries.addDataItem(round(chartData.getWater1()));
			soilChartSeries.addDataItem(round(chartData.getWater2()));
			soilChartSeries.addDataItem(round(chartData.getWater3()));
			
			chartInfo.addBarChartSeries(soilChartSeries);
		} else if (param.getSensor() == SensorType.토양온도) {
			BarChartSeries soilChartSeries = new BarChartSeries(type.getTitleTemp());
			soilChartSeries.addDataItem(round(chartData.getTemp1()));
			soilChartSeries.addDataItem(round(chartData.getTemp2()));
			soilChartSeries.addDataItem(round(chartData.getTemp3()));
			
			chartInfo.addBarChartSeries(soilChartSeries);
		}
		
		return chartInfo;
		
//		BarChartSeries soilAChartSeries = new BarChartSeries("토양수분 A");
//		soilAChartSeries.addDataItem(22.9f);
//		soilAChartSeries.addDataItem(27.3f);
//		soilAChartSeries.addDataItem(21.8f);
//		
//		BarChartSeries soilBChartSeries = new BarChartSeries("토양수분 B");
//		soilBChartSeries.addDataItem(21.4f);
//		soilBChartSeries.addDataItem(14.6f);
//		soilBChartSeries.addDataItem(22.1f);
//		
//		BarChartSeries soilCChartSeries = new BarChartSeries("토양수분 C");
//		soilCChartSeries.addDataItem(10.6f);
//		soilCChartSeries.addDataItem(34.3f);
//		soilCChartSeries.addDataItem(44.3f);
//		
//		BarChartSeries soilDChartSeries = new BarChartSeries("토양수분 D");
//		soilDChartSeries.addDataItem(11.9f);
//		soilDChartSeries.addDataItem(13.7f);
//		soilDChartSeries.addDataItem(23.5f);
//		
//		BarChartSeries soilEChartSeries = new BarChartSeries("토양수분 E");
//		soilEChartSeries.addDataItem(11.9f);
//		soilEChartSeries.addDataItem(13.7f);
//		soilEChartSeries.addDataItem(25.6f);
//		
//		soilAChartInfo.addBarChartSeries(soilAChartSeries);
//		soilBChartInfo.addBarChartSeries(soilBChartSeries);
//		soilCChartInfo.addBarChartSeries(soilCChartSeries);
//		soilDChartInfo.addBarChartSeries(soilDChartSeries);
//		soilEChartInfo.addBarChartSeries(soilEChartSeries);
	}
	
	private float round(float value) {
		return (float) (Math.round(value * 100) / 100.0);
	}
	
	public void createLineChart(PointType type, PointInfo pointInfo, SearchParam param, DashboardInfo dashboardInfo) {
		ChartInfo tempChartInfo = new ChartInfo(SensorType.토양온도);
		ChartInfo waterChartInfo = new ChartInfo(SensorType.토양수분);
		
		LineChartSeries temp1ChartSeries = new LineChartSeries(LocationType.상층.name());
		LineChartSeries temp2ChartSeries = new LineChartSeries(LocationType.중층.name());
		LineChartSeries temp3ChartSeries = new LineChartSeries(LocationType.하층.name());
		
		LineChartSeries water1ChartSeries = new LineChartSeries(LocationType.상층.name());
		LineChartSeries water2ChartSeries = new LineChartSeries(LocationType.중층.name());
		LineChartSeries water3ChartSeries = new LineChartSeries(LocationType.하층.name());
		
		measurementService.getList(pointInfo.getPointSq(), param.getDaysDate(), param.getEndDate()).forEach(data -> {
			tempChartInfo.addCategory(dateFormat.format(data.getMeasDt()));
			waterChartInfo.addCategory(dateFormat.format(data.getMeasDt()));
			
			temp1ChartSeries.addDataItem(data.getTempCh1());
			temp2ChartSeries.addDataItem(data.getTempCh2());
			temp3ChartSeries.addDataItem(data.getTempCh3());
			
			water1ChartSeries.addDataItem(data.getVwcCh1());
			water2ChartSeries.addDataItem(data.getVwcCh2());
			water3ChartSeries.addDataItem(data.getVwcCh3());
		});
		
		tempChartInfo.addListChartSeries(temp1ChartSeries);
		tempChartInfo.addListChartSeries(temp2ChartSeries);
		tempChartInfo.addListChartSeries(temp3ChartSeries);
		
		waterChartInfo.addListChartSeries(water1ChartSeries);
		waterChartInfo.addListChartSeries(water2ChartSeries);
		waterChartInfo.addListChartSeries(water3ChartSeries);
		
		if (type == PointType.A) {
			dashboardInfo.setTempALineChart(tempChartInfo);
			dashboardInfo.setWaterALineChart(waterChartInfo);
		} else if (type == PointType.B) {
			dashboardInfo.setTempBLineChart(tempChartInfo);
			dashboardInfo.setWaterBLineChart(waterChartInfo);
		} else if (type == PointType.C) {
			dashboardInfo.setTempCLineChart(tempChartInfo);
			dashboardInfo.setWaterCLineChart(waterChartInfo);
		} else if (type == PointType.D) {
			dashboardInfo.setTempDLineChart(tempChartInfo);
			dashboardInfo.setWaterDLineChart(waterChartInfo);
		} else if (type == PointType.E) {
			dashboardInfo.setTempELineChart(tempChartInfo);
			dashboardInfo.setWaterELineChart(waterChartInfo);
		}
	}
	
	/**
	 * 대시보드 Line 차트 생성
	 * @param dashboardInfo
	 */
	public void createLineChart(DashboardInfo dashboardInfo) {
		ChartInfo tempAChartInfo = new ChartInfo(SensorType.토양온도);
		ChartInfo waterAChartInfo = new ChartInfo(SensorType.토양수분);
		
		String[] categories = {"05-01", "05-02", "05-03", "05-04", "05-05", "05-06", "05-07"};
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
