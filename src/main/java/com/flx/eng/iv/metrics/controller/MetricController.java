package com.flx.eng.iv.metrics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flx.eng.iv.metrics.db.entity.Metric;
import com.flx.eng.iv.metrics.request.CreateMetericRequest;
import com.flx.eng.iv.metrics.response.Response;
import com.flx.eng.iv.metrics.response.Statistics;
import com.flx.eng.iv.metrics.service.IMetricService;
import com.flx.eng.iv.metrics.utils.MSConstants;
import com.flx.eng.iv.metrics.utils.MSException;
import com.flx.eng.iv.metrics.utils.MSUtils;

@RestController
public class MetricController {

	private final static Logger LOG = LoggerFactory.getLogger(MetricController.class);
	@Autowired
	private IMetricService metricService;

	@RequestMapping(method = RequestMethod.GET, value = "metrics")
	public ResponseEntity<Object> getMetrics() {
		LOG.info("/metrics");
		Response body;
		try {
			List<Metric> metrics = metricService.getMetrics();
			return new ResponseEntity<Object>(metrics, HttpStatus.OK);
		} catch (Exception ex) {
			body = new Response(MSConstants.ERROR, MSUtils.stackTraceToString(ex));
			return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "metrics/{metric}")
	public ResponseEntity<Object> getMetrics(@PathVariable String metric) {
		LOG.info("/metric/{metric}");
		Response body;
		try {
			List<Metric> metrics = metricService.getMetrics(metric);
			return new ResponseEntity<Object>(metrics, HttpStatus.OK);
		} catch (Exception ex) {
			body = new Response(MSConstants.ERROR, MSUtils.stackTraceToString(ex));
			return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "metrics/statistics/{metric}")
	public ResponseEntity<Object> getStatistics(@PathVariable String metric) {
		LOG.info("metrics/statistics/{metric}");
		Response body;
		try {
			Statistics stats = metricService.getStatistics(metric);
			return new ResponseEntity<Object>(stats, HttpStatus.OK);
		} catch (MSException ex) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} catch (Exception ex) {
			body = new Response(MSConstants.ERROR, MSUtils.stackTraceToString(ex));
			return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/metrics")
	public ResponseEntity<Response> createMetric(@RequestBody CreateMetericRequest req) {
		LOG.info("/metrics");
		Response body;
		try {
			metricService.addMetric(req);
			body = new Response(MSConstants.SUCCESS, null);
			return new ResponseEntity<Response>(body, HttpStatus.OK);
		} catch (MSException ex) {
			body = new Response(MSConstants.ERROR, ex.getMessage());
			return new ResponseEntity<Response>(body, HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			body = new Response(MSConstants.ERROR, MSUtils.stackTraceToString(ex));
			return new ResponseEntity<Response>(body, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/metrics/{metric}/{value}")
	public ResponseEntity<Response> createMetric(@PathVariable String metric, @PathVariable float value) {
		LOG.info("/metrics/{metric}/{value}");
		Response body;
		try {
			MSUtils.validateCreateMetricRequest(metric, value);
			metricService.addMetric(metric, value);
			body = new Response(MSConstants.SUCCESS, null);
			return new ResponseEntity<Response>(body, HttpStatus.OK);
		} catch (MSException ex) {
			body = new Response(MSConstants.ERROR, ex.getMessage());
			return new ResponseEntity<Response>(body, HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			body = new Response(MSConstants.ERROR, MSUtils.stackTraceToString(ex));
			return new ResponseEntity<Response>(body, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
