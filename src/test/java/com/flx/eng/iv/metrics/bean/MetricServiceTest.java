package com.flx.eng.iv.metrics.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.flx.eng.iv.metrics.db.entity.Metric;
import com.flx.eng.iv.metrics.request.CreateMetericRequest;
import com.flx.eng.iv.metrics.response.Statistics;
import com.flx.eng.iv.metrics.service.MetricRepository;
import com.flx.eng.iv.metrics.utils.MSException;

@RunWith(MockitoJUnitRunner.class)
public class MetricServiceTest {

	@InjectMocks
	private MetricService metricService;

	@Mock
	private MetricRepository metricRepository;

	@Test
	public void testGetMetrics() {
		List<Metric> expected = getMockMetrics();
		Mockito.when(metricRepository.findAll()).thenReturn(expected);
		List<Metric> actual = metricService.getMetrics();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetMetricsForMetricName() {
		List<Metric> expected = getMockMetrics();
		Mockito.when(metricRepository.findAllByMetric("Google")).thenReturn(expected);
		List<Metric> actual = metricService.getMetrics("Google");
		assertEquals(expected, actual);
	}

	@Test
	public void testGetStatisticsForMetricName() throws MSException {
		List<Metric> metrics = getMockMetrics();
		Mockito.when(metricRepository.findAllByMetric("Google")).thenReturn(metrics);
		Statistics actual = metricService.getStatistics("Google");
		assertTrue(actual.getMin() == 1.25f);
		assertTrue(actual.getMax() == 4.25f);
		assertTrue(actual.getMean() == 2.75f);
		assertTrue(actual.getMedian() == 2.75f);

	}

	@Test
	public void testGetStatisticsForMetricNameError() throws MSException {
		try {
			List<Metric> metrics = new ArrayList<>();
			Mockito.when(metricRepository.findAllByMetric("Google")).thenReturn(metrics);
			metricService.getStatistics("Google");
			fail();
		} catch (Exception ex) {
			assertTrue(true);
		}

	}

	@Test
	public void testAddMetrics() {
		try {
			List<Metric> expected = getMockMetrics();
			Mockito.when(metricRepository.saveAll(Mockito.any())).thenReturn(expected);
			metricService.addMetric(getMockCreateMetericRequest());
			assertTrue(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddMetricsBadReq() {
		try {
			metricService.addMetric(getMockCreateMetericBadRequest());
			fail();
		} catch (Exception ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddMetricForMetricName() {
		try {
			Mockito.when(metricRepository.save(Mockito.any())).thenReturn(null);
			metricService.addMetric("Google", 2.2f);
			assertTrue(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	private CreateMetericRequest getMockCreateMetericRequest() {
		return new CreateMetericRequest("Facebook", new float[] { 1.25f, 2.25f, 3.25f, 4.25f });
	}

	private CreateMetericRequest getMockCreateMetericBadRequest() {
		return new CreateMetericRequest("Facebook", new float[] {});
	}

	private List<Metric> getMockMetrics() {
		List<Metric> metrics = new ArrayList<Metric>();
		for (int i = 1; i < 5; i++) {
			Metric m = new Metric();
			m.setId(i);
			m.setMetric("Google");
			m.setValue(i + 0.25f);
			metrics.add(m);
		}
		return metrics;
	}

}
