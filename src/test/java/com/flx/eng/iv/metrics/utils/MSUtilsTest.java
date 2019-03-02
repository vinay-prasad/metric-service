package com.flx.eng.iv.metrics.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MSUtilsTest {

	MSUtils msUtils = new MSUtils();

	@Test(expected = MSException.class)
	public void testValidateCreateMetricRequestBad() throws MSException {
		MSUtils.validateCreateMetricRequest(null, 0);
	}

	@Test
	public void testGetMedian() {
		float actual = MSUtils.getMedian(new float[] { 1.2f, 1.3f, 1.4f, 1.5f, 1.6f }, 5);
		assertTrue(actual == 1.4f);
	}

}
