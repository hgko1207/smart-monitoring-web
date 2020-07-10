package net.woori.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.start.domain.chart.WeatherChartData;
import net.woori.start.domain.db.Weather;

public interface WeatherRepository extends DefaultRepository<Weather, Integer> {

	final String SELECT_HOURLY = "TO_TIMESTAMP(to_char(meas_dt, 'YYYY-MM-DD HH24:00:00'), 'YYYY-MM-DD HH24:MI:SS') date, "
			+ "AVG(temp_150) as temp, AVG(hd_150) as hum, AVG(arvlty_300) as arvlty, "
			+ "AVG(afp) as afp, AVG(solrad_qy) as solradQy, AVG(soil_mitr_10) as soilMitr";
	
	final String BETWEEN = "meas_dt BETWEEN to_timestamp(?1, 'YYYY-MM-DD HH24:MI:SS') and to_timestamp(?2, 'YYYY-MM-DD HH24:MI:SS') + interval '1'";
	final String GROUP_BY_HOURLY = " GROUP BY date";
	final String ORDER_BY = " ORDER BY date";
	
	@Query(value = "SELECT " + SELECT_HOURLY + " FROM tb_weather WHERE " + BETWEEN + GROUP_BY_HOURLY + ORDER_BY, nativeQuery = true)
	List<WeatherChartData> getChartList(String startDate, String endDate);
	
	@Query(value = "SELECT * FROM tb_weather ORDER BY meas_dt desc limit 1", nativeQuery = true)
	Weather getRecentData();

	@Query(value = "SELECT * FROM tb_weather WHERE " + BETWEEN  + " ORDER BY meas_dt", nativeQuery = true)
	List<Weather> getList(String startDate, String endDate);

}
