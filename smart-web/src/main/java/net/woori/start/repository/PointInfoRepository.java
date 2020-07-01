package net.woori.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import net.woori.start.domain.db.PointInfo;

public interface PointInfoRepository extends DefaultRepository<PointInfo, Integer> {

	PointInfo findByPointNmContaining(String pointNm);

	@Query(value = "SELECT * FROM tb_sf_info_point WHERE point_nm like ?1", nativeQuery = true)
	List<PointInfo> getList(String name);
}
