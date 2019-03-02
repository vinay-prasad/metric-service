package com.flx.eng.iv.metrics.db.entity;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.flx.eng.iv.metrics.utils.PojoTestUtils;

public class MetricTest {

	@Test
	public void test() {
		Metric m = new Metric(1, "metric", 2.2f);
		assertNotNull(m);
		PojoTestUtils.validateAccessors(Metric.class);
	}
}
