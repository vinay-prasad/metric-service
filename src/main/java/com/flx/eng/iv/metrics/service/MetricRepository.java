package com.flx.eng.iv.metrics.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.flx.eng.iv.metrics.db.entity.Metric;

public interface MetricRepository extends CrudRepository<Metric, String> {
	
	List<Metric> findAllByMetric(String name);
	float[] findAllValuesByMetric(String name);

}