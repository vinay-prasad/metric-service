package com.flx.eng.iv.metrics.request;

import static org.junit.Assert.*;

import org.junit.Test;

import com.flx.eng.iv.metrics.utils.PojoTestUtils;

public class CreateMetericRequestTest {

	@Test
	public void test() {
		CreateMetericRequest req = new CreateMetericRequest();
		assertNotNull(req);
		PojoTestUtils.validateAccessors(CreateMetericRequest.class);
	}

}
