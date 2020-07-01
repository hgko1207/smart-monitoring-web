package net.woori.start.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.MapInfo;
import net.woori.start.domain.db.MeasurementLog;
import net.woori.start.domain.db.PointInfo;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.MeasurementLogService;
import net.woori.start.service.PointInfoService;

@Service
public class MapService {
	
	@Autowired
	private PointInfoService pointInfoService;
	
	@Autowired
	private MeasurementLogService measurementService;

	public List<MapInfo> createMapInfo() {
		List<MapInfo> mapInfos = new ArrayList<>();
		
		pointInfoService.getList().forEach(data -> {
			MapInfo mapInfo = new MapInfo();
			mapInfo.setPoint(data.getPointNm());
			mapInfo.setLatitude(data.getLocLat());
			mapInfo.setLongitude(data.getLocLng());
			
			MeasurementLog measurementLog = measurementService.getCurrentData(data.getPointSq());
			if (measurementLog != null) {
				mapInfo.setTemp1(measurementLog.getTempCh1());
				mapInfo.setTemp2(measurementLog.getTempCh2());
				mapInfo.setTemp3(measurementLog.getTempCh3());
				mapInfo.setWater1(measurementLog.getVwcCh1());
				mapInfo.setWater2(measurementLog.getVwcCh2());
				mapInfo.setWater3(measurementLog.getVwcCh3());
			}
			
			mapInfos.add(mapInfo);
		});
		
		return mapInfos;
	}
	
	public MapInfo createMapInfo(SearchParam param) {
		PointInfo pointInfo = pointInfoService.get("A " + param.getSensorPoint().name());
		
		MapInfo mapInfo = new MapInfo();
		mapInfo.setPoint(pointInfo.getPointNm());
		mapInfo.setLatitude(pointInfo.getLocLat());
		mapInfo.setLongitude(pointInfo.getLocLng());
		
		return mapInfo;
	}
}
