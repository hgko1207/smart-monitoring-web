package net.woori.start.repository;

import net.woori.start.domain.db.PointInfo;

public interface PointInfoRepository extends DefaultRepository<PointInfo, Integer> {

	PointInfo findByPointNmContaining(String pointNm);

}
