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
	 * @param type
	 * @param param
	 * @param chartData
	 * @return
	 */
	public ChartInfo createBarChart(PointType type, SearchParam param, ChartData chartData) {
		ChartInfo chartInfo = new ChartInfo();
		String[] categories = {"50cm", "30cm", "10cm"};
		for (String data : categories) {
			chartInfo.addCategory(data);
		}
		
		if (param.getSensor() == SensorType.토양수분) {
			BarChartSeries soilChartSeries = new BarChartSeries(param.getSensor(), type.getTitleWater());
			soilChartSeries.addDataItem(round(chartData.getWater1()));
			soilChartSeries.addDataItem(round(chartData.getWater2()));
			soilChartSeries.addDataItem(round(chartData.getWater3()));
			
			chartInfo.addBarChartSeries(soilChartSeries);
		} else if (param.getSensor() == SensorType.토양온도) {
			BarChartSeries soilChartSeries = new BarChartSeries(param.getSensor(), type.getTitleTemp());
			soilChartSeries.addDataItem(round(chartData.getTemp1()));
			soilChartSeries.addDataItem(round(chartData.getTemp2()));
			soilChartSeries.addDataItem(round(chartData.getTemp3()));
			
			chartInfo.addBarChartSeries(soilChartSeries);
		}
		
		return chartInfo;
	}
	
	private float round(float value) {
		return (float) (Math.round(value * 100) / 100.0);
	}
	
	/**
	 * 대시보드 Line 차트 생성
	 * @param type
	 * @param pointInfo
	 * @param param
	 * @param dashboardInfo
	 */
	public void createLineChart(PointType type, PointInfo pointInfo, SearchParam param, DashboardInfo dashboardInfo) {
		ChartInfo tempChartInfo = new ChartInfo(SensorType.토양온도);
		ChartInfo waterChartInfo = new ChartInfo(SensorType.토양수분);
		
		LineChartSeries temp1ChartSeries = new LineChartSeries(LocationType.상층.getName());
		LineChartSeries temp2ChartSeries = new LineChartSeries(LocationType.중층.getName());
		LineChartSeries temp3ChartSeries = new LineChartSeries(LocationType.하층.getName());
		
		LineChartSeries water1ChartSeries = new LineChartSeries(LocationType.상층.getName());
		LineChartSeries water2ChartSeries = new LineChartSeries(LocationType.중층.getName());
		LineChartSeries water3ChartSeries = new LineChartSeries(LocationType.하층.getName());
		
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
}
