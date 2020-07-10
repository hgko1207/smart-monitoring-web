package net.woori.start.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.db.MeasurementLog;
import net.woori.start.repository.MeasurementLogRepository;
import net.woori.start.service.MeasurementLogService;

@Transactional
@Service
public class MeasurementLogServiceImpl implements MeasurementLogService {
	
	@Autowired
	private MeasurementLogRepository measurementLogRepository;

	@Override
	public MeasurementLog get(Integer id) {
		return measurementLogRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<MeasurementLog> getList() {
		return measurementLogRepository.findAll();
	}

	@Override
	public boolean regist(MeasurementLog domain) {
		if (isNew(domain)) {
			return measurementLogRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(MeasurementLog domain) {
		if (!isNew(domain)) {
			return measurementLogRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(Integer id) {
		measurementLogRepository.deleteById(id);
		return true;
	}

	private boolean isNew(MeasurementLog domain) {
		return !measurementLogRepository.existsById(domain.getPointSq());
	}

	@Override
	public List<MeasurementLog> getList(int pointSq, String startDate, String endDate) {
		return measurementLogRepository.getList(startDate, endDate, pointSq);
	}

	@Override
	public List<ChartData> getDailyList(int pointSq, String startDate, String endDate) {
		return measurementLogRepository.getDailyList(startDate, endDate, pointSq);
	}

	@Override
	public MeasurementLog getCurrentData(int pointSq) {
		return measurementLogRepository.getCurrentData(pointSq);
	}

	@Override
	public List<ChartData> getHourlyList(int pointSq, String startDate, String endDate) {
		return measurementLogRepository.getHourlyList(startDate, endDate, pointSq);
	}

	@Override
	public MeasurementLog getCurrentData(int pointSq, String startDate, String endDate) {
		return measurementLogRepository.getCurrentData(startDate, endDate, pointSq);
	}
}
