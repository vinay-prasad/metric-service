package com.flx.eng.iv.metrics.request;

public class CreateMetericRequest {

	String metric;
	float[] values;

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	public CreateMetericRequest(String metric, float[] values) {
		super();
		this.metric = metric;
		this.values = values;
	}

	public CreateMetericRequest() {
		super();
	}
}
