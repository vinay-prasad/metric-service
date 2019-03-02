package com.flx.eng.iv.metrics.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.flx.eng.iv.metrics.bean.MetricService;
import com.flx.eng.iv.metrics.db.entity.Metric;
import com.flx.eng.iv.metrics.request.CreateMetericRequest;
import com.flx.eng.iv.metrics.response.Response;
import com.flx.eng.iv.metrics.response.Statistics;
import com.flx.eng.iv.metrics.utils.MSException;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MetricController.class, secure = false)
public class MetricControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MetricService metricService;

	@Test
	public void testGetMetrics() throws Exception {
		List<Metric> metrics = getMockMetrics();
		Mockito.when(metricService.getMetrics()).thenReturn(metrics);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 200);
		System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"id\":1,\"metric\":\"Google\",\"value\":1.25},{\"id\":2,\"metric\":\"Google\",\"value\":2.25},{\"id\":3,\"metric\":\"Google\",\"value\":3.25},{\"id\":4,\"metric\":\"Google\",\"value\":4.25}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetMetricsError() throws Exception {
		Mockito.when(metricService.getMetrics()).thenThrow(new RuntimeException("Internal Server Error"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 500);
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString().contains("error"));
	}

	@Test
	public void testGetMetricsForMetric() throws Exception {
		List<Metric> metrics = getMockMetrics();
		Mockito.when(metricService.getMetrics("Google")).thenReturn(metrics);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/Google")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 200);
		System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"id\":1,\"metric\":\"Google\",\"value\":1.25},{\"id\":2,\"metric\":\"Google\",\"value\":2.25},{\"id\":3,\"metric\":\"Google\",\"value\":3.25},{\"id\":4,\"metric\":\"Google\",\"value\":4.25}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetMetricsForMetricError() throws Exception {
		Mockito.when(metricService.getMetrics("Google")).thenThrow(new RuntimeException("Internal Server Error"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/Google")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 500);
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString().contains("error"));
	}

	@Test
	public void testGetStatistics() throws Exception {
		Statistics stats = getMockStatistics();
		Mockito.when(metricService.getStatistics("Google")).thenReturn(stats);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/statistics/Google")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 200);
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{\"mean\":2.75,\"median\":2.75,\"min\":1.25,\"max\":4.25}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetStatisticsError() throws Exception {
		Mockito.when(metricService.getStatistics("Google")).thenThrow(new RuntimeException("INternal Server Error"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/statistics/Google")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 500);
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString().contains("error"));
	}

	@Test
	public void testGetStatisticsMSError() throws Exception {
		Mockito.when(metricService.getStatistics("Google")).thenThrow(new MSException("Internal Server Error"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/statistics/Google")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 204);
	}

	@Test
	public void testPostMetric() throws Exception {
		String expected = "{\"message\":\"success\",\"error\":null}";
		doNothing().when(metricService).addMetric(Mockito.anyString(), Mockito.anyFloat());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/metrics/Facebook/2.3")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 200);
		System.out.println(result.getResponse().getContentAsString());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testPostMetricError() throws Exception {
		doThrow(new RuntimeException("Internal Error")).when(metricService).addMetric(Mockito.anyString(), Mockito.anyFloat());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/metrics/Facebook/2.3")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 500);
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString().contains("error"));
	}
	
	@Test
	public void testCreateMetric() throws Exception {
		CreateMetericRequest req = getMockCreateMetericRequest();
		String expected = "{\"message\":\"success\",\"error\":null}";
		doNothing().when(metricService).addMetric(req);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/metrics/")
				.content("{\"metric\": \"Facebook\",\"values\": [1.25,2.25,3.25,4.25]}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 200);
		System.out.println(result.getResponse().getContentAsString());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreateMetricError() throws Exception {
		CreateMetericRequest req = getMockCreateMetericBadRequest();
		doThrow(new RuntimeException("Internal Error")).when(metricService).addMetric(Mockito.any());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/metrics/")
				.content("{\"metric\": \"Facebook\",\"values\": []}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 500);
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString().contains("error"));
	}

	@Test
	public void testCreateMetricMSError() throws Exception {
		doThrow(new MSException("Internal Error")).when(metricService).addMetric(Mockito.any());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/metrics/")
				.content("{\"metric\": \"Facebook\",\"values\": []}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), 400);
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString().contains("error"));
	}
	private CreateMetericRequest getMockCreateMetericRequest() {
		return new CreateMetericRequest("Facebook", new float[] { 1.25f, 2.25f, 3.25f, 4.25f });
	}

	private CreateMetericRequest getMockCreateMetericBadRequest() {
		return new CreateMetericRequest("Facebook", new float[] {});
	}

	private Statistics getMockStatistics() {
		Statistics stats = new Statistics();
		stats.setMin(1.25f);
		stats.setMax(4.25f);
		stats.setMean(2.75f);
		stats.setMedian(2.75f);
		return stats;
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
