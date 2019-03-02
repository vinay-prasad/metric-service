package com.flx.eng.iv.metrics.service;

import java.util.List;

import com.flx.eng.iv.metrics.db.entity.Metric;
import com.flx.eng.iv.metrics.request.CreateMetericRequest;
import com.flx.eng.iv.metrics.response.Statistics;
import com.flx.eng.iv.metrics.utils.MSException;

public interface IMetricService {

	public List<Metric> getMetrics();

	public List<Metric> getMetrics(String metric);

	public Statistics getStatistics(String metric) throws MSException;

	public void addMetric(String metric, float value);

	public void addMetric(CreateMetericRequest req) throws MSException;

}
