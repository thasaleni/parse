package com.ef.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ef.entities.Log;
@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {
	@Query("SELECT distinct l.ip from Log l where l.date between :startDate and :endDate group by l.ip having count(l.ip) > :count")
	List<Log> findLogs(@Param("startDate") Date start, @Param("endDate") Date end,@Param("count") Integer count);

}
