package net.woori.start.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DefaultRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
	
	final String BETWEEN = "meas_dt BETWEEN to_timestamp(?1, 'YYYY-MM-DD HH24:MI:SS') and to_timestamp(?2, 'YYYY-MM-DD HH24:MI:SS') + interval '1'";
	final String ORDER_BY = " ORDER BY meas_dt";
}
