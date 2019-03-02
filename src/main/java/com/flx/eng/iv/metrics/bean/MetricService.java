package com.flx.eng.iv.metrics.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flx.eng.iv.metrics.db.entity.Metric;
import com.flx.eng.iv.metrics.request.CreateMetericRequest;
import com.flx.eng.iv.metrics.response.Statistics;
import com.flx.eng.iv.metrics.service.IMetricService;
import com.flx.eng.iv.metrics.service.MetricRepository;
import com.flx.eng.iv.metrics.utils.MSException;
import com.flx.eng.iv.metrics.utils.MSUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MetricService implements IMetricService {
	private final static Logger LOG = LoggerFactory.getLogger(MetricService.class);

	@Autowired
	private MetricRepository metricRepository;

	@Override
	public List<Metric> getMetrics() {
		LOG.info("getMetrics() start");
		List<Metric> metrics = new ArrayList<>();
		metricRepository.findAll().forEach(metrics::add);
		return metrics;
	}

	@Override
	public List<Metric> getMetrics(String metric) {
		LOG.info("getMetrics(metric) start");
		List<Metric> metrics = new ArrayList<>();
		metricRepository.findAllByMetric(metric).forEach(metrics::add);
		return metrics;
	}

	@Override
	public Statistics getStatistics(String metric) throws MSException {
		LOG.info("getStatistics() start");
		Statistics stats = new Statistics();
		List<Metric> metrics = this.getMetrics(metric);
		float[] values = getValues(metrics);
		Arrays.sort(values);
		stats.setMin(values[0]);
		stats.setMax(values[values.length - 1]);
		stats.setMean(MSUtils.getMean(values, values.length));
		stats.setMedian(MSUtils.getMedian(values, values.length));
		return stats;
	}

	private float[] getValues(List<Metric> metrics) throws MSException {
		LOG.debug("getValues() start");
		float[] values = null;
		if (metrics != null && metrics.size() > 0) {
			values = new float[metrics.size()];
			for (int i = 0; i < metrics.size(); i++) {
				values[i] = metrics.get(i).getValue();
			}
			return values;
		} else {
			throw new MSException("No data found for given metrics");
		}
	}

	@Override
	public void addMetric(CreateMetericRequest req) throws MSException {
		LOG.info("createMetric() start");
		MSUtils.validateCreateMetricRequest(req);
		String metric = req.getMetric();
		float[] values = req.getValues();
		List<Metric> metrics = new ArrayList<>();
		for (float value : values) {
			Metric m = new Metric();
			// This should be taken care by auto-generated sequence by most of the DBs
			m.setId(MSUtils.getID());
			m.setMetric(metric);
			m.setValue(value);
			metrics.add(m);
		}
		metricRepository.saveAll(metrics);
		LOG.info("createMetric() end");
	}

	@Override
	public void addMetric(String metric, float value) {
		LOG.info("createMetric(metric, value) start");
		Metric m = new Metric();
		// This should be taken care by auto-generated sequence by most of the DBs
		m.setId(MSUtils.getID());
		m.setMetric(metric);
		m.setValue(value);
		metricRepository.save(m);
		LOG.info("createMetric(metric, value) end");
	}
}
