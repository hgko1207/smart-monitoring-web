package net.woori.start.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.MapInfo;
import net.woori.start.domain.db.Measurement;
import net.woori.start.service.MeasurementService;
import net.woori.start.service.PointInfoService;

@Service
public class MapService {
	
	@Autowired
	private PointInfoService pointInfoService;
	
	@Autowired
	private MeasurementService measurementService;

	public List<MapInfo> createMapInfo() {
		List<MapInfo> mapInfos = new ArrayList<>();
		
		pointInfoService.getList().forEach(data -> {
			if (!data.getPointNm().contains("사람")) {
				MapInfo mapInfo = new MapInfo();
				mapInfo.setPoint(data.getPointNm());
				mapInfo.setLatitude(data.getLocLat());
				mapInfo.setLongitude(data.getLocLng());
				
				Measurement measurement = measurementService.getCurrentData(data.getPointSq());
				if (measurement != null) {
					mapInfo.setTemp1(measurement.getTempCh1());
					mapInfo.setTemp2(measurement.getTempCh2());
					mapInfo.setTemp3(measurement.getTempCh3());
					mapInfo.setWater1(measurement.getVwcCh1());
					mapInfo.setWater2(measurement.getVwcCh2());
					mapInfo.setWater3(measurement.getVwcCh3());
				}
				
				mapInfos.add(mapInfo);
			}
		});
		
		return mapInfos;
	}
}
