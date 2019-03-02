package com.flx.eng.iv.metrics.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Metric {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String metric;
	private float value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Metric(int id, String metric, float value) {
		super();
		this.id = id;
		this.metric = metric;
		this.value = value;
	}

	public Metric() {
		super();
	}
}
