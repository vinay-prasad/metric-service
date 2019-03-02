package com.flx.eng.iv.metrics.response;

import static org.junit.Assert.*;

import org.junit.Test;

import com.flx.eng.iv.metrics.utils.PojoTestUtils;

public class StatisticsTest {

	@Test
	public void test() {
		Statistics s = new Statistics(1.2f, 1.2f, 1.2f, 1.2f);
		assertNotNull(s);
		PojoTestUtils.validateAccessors(Statistics.class);
	}

}
