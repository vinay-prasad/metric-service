package com.flx.eng.iv.metrics.response;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.flx.eng.iv.metrics.utils.PojoTestUtils;

public class ResponseTest {

	@Test
	public void test() {
		Response r = new Response("message", "error");
		assertNotNull(r);
		PojoTestUtils.validateAccessors(Response.class);
	}

}
