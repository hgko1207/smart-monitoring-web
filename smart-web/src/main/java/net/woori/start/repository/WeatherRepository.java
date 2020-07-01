package net.woori.start.repository;

import org.springframework.data.jpa.repository.Query;

import net.woori.start.domain.db.Weather;

public interface WeatherRepository extends DefaultRepository<Weather, Integer> {

	@Query(value = "SELECT * FROM tb_weather ORDER BY date desc limit 1", nativeQuery = true)
	Weather getData();

}
