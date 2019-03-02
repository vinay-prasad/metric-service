package com.flx.eng.iv.metrics.response;

public class Statistics {

	private float mean;
	private float median;
	private float min;
	private float max;

	public float getMean() {
		return mean;
	}

	public void setMean(float mean) {
		this.mean = mean;
	}

	public float getMedian() {
		return median;
	}

	public void setMedian(float median) {
		this.median = median;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public Statistics(float mean, float median, float min, float max) {
		super();
		this.mean = mean;
		this.median = median;
		this.min = min;
		this.max = max;
	}

	public Statistics() {
		super();
		// TODO Auto-generated constructor stub
	}

}
