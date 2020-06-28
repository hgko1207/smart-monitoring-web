package net.woori.start.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.db.Measurement;
import net.woori.start.repository.MeasurementRepository;
import net.woori.start.service.MeasurementService;

@Transactional
@Service
public class MeasurementServiceImpl implements MeasurementService {
	
	@Autowired
	private MeasurementRepository measurementRepository;

	@Override
	public Measurement get(Integer id) {
		return measurementRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Measurement> getList() {
		return measurementRepository.findAll();
	}

	@Override
	public boolean regist(Measurement domain) {
		if (isNew(domain)) {
			return measurementRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(Measurement domain) {
		if (!isNew(domain)) {
			return measurementRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(Integer id) {
		measurementRepository.deleteById(id);
		return true;
	}

	private boolean isNew(Measurement domain) {
		return !measurementRepository.existsById(domain.getPointSq());
	}

	@Override
	public List<Measurement> getList(int pointSq, String startDate, String endDate) {
		return measurementRepository.findByPointSq(startDate, endDate, pointSq);
	}

	@Override
	public List<ChartData> getDailyList(int pointSq, String startDate, String endDate) {
		return measurementRepository.getDailyList(startDate, endDate, pointSq);
	}

	@Override
	public Measurement getCurrentData(int pointSq) {
		return measurementRepository.getCurrentData(pointSq);
	}
}
