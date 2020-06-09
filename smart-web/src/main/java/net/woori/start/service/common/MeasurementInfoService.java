package net.woori.start.service.common;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.EnumType.LocationType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.chart.ChartInfo;
import net.woori.start.domain.chart.LineChartSeries;
import net.woori.start.domain.db.PointInfo;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.MeasurementService;
import net.woori.start.service.PointInfoService;

@Service
public class MeasurementInfoService {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH시");
	
	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private PointInfoService pointInfoService;

	public ChartInfo createChartInfo(SearchParam param) {
		ChartInfo chartInfo = new ChartInfo(param.getSensor().name());
		
		if (param.getPoint().equals("전체")) {
			String[] categories = {"2020/05/01", "2020/05/02", "2020/05/03", "2020/05/04", "2020/05/05", "2020/05/06", "2020/05/07"};
			for (String data : categories) {
				chartInfo.addCategory(data);
			}
			
			LineChartSeries chartSeriesA = new LineChartSeries("A지점");
			chartSeriesA.addDataItem(14);
			chartSeriesA.addDataItem(18);
			chartSeriesA.addDataItem(20);
			chartSeriesA.addDataItem(16);
			chartSeriesA.addDataItem(12);
			chartSeriesA.addDataItem(17);
			chartSeriesA.addDataItem(15);
			
			LineChartSeries chartSeriesB = new LineChartSeries("B지점");
			chartSeriesB.addDataItem(16);
			chartSeriesB.addDataItem(12);
			chartSeriesB.addDataItem(21);
			chartSeriesB.addDataItem(16);
			chartSeriesB.addDataItem(24);
			chartSeriesB.addDataItem(22);
			chartSeriesB.addDataItem(20);
			
			LineChartSeries chartSeriesC = new LineChartSeries("C지점");
			chartSeriesC.addDataItem(12);
			chartSeriesC.addDataItem(17);
			chartSeriesC.addDataItem(23);
			chartSeriesC.addDataItem(20);
			chartSeriesC.addDataItem(22);
			chartSeriesC.addDataItem(24);
			chartSeriesC.addDataItem(17);
			
			LineChartSeries chartSeriesD = new LineChartSeries("D지점");
			
			LineChartSeries chartSeriesE = new LineChartSeries("E지점");
			
			chartInfo.addListChartSeries(chartSeriesA);
			chartInfo.addListChartSeries(chartSeriesB);
			chartInfo.addListChartSeries(chartSeriesC);
			chartInfo.addListChartSeries(chartSeriesD);
			chartInfo.addListChartSeries(chartSeriesE);
		} else {
			PointInfo pointInfo = pointInfoService.get(Integer.parseInt(param.getPoint()));
			LineChartSeries chartSeries = new LineChartSeries(pointInfo.getPointNm());
			
			measurementService.getList(pointInfo.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				chartInfo.addCategory(dateFormat.format(data.getMeasDt()));
				if (param.getSensor() == SensorType.토양수분) {
					if (param.getLocation() == LocationType.상층) {
						chartSeries.addDataItem(data.getVwcCh1());
					} else if (param.getLocation() == LocationType.중층) {
						chartSeries.addDataItem(data.getVwcCh2());
					} else if (param.getLocation() == LocationType.하층) {
						chartSeries.addDataItem(data.getVwcCh3());
					}
				} else if (param.getSensor() == SensorType.토양온도) {
					if (param.getLocation() == LocationType.상층) {
						chartSeries.addDataItem(data.getTempCh1());
					} else if (param.getLocation() == LocationType.중층) {
						chartSeries.addDataItem(data.getTempCh2());
					} else if (param.getLocation() == LocationType.하층) {
						chartSeries.addDataItem(data.getTempCh3());
					}
				}
			});
			
			chartInfo.addListChartSeries(chartSeries);
		}
		
		return chartInfo;
	}
}
