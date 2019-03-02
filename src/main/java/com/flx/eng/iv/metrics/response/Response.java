package com.flx.eng.iv.metrics.response;

public class Response {
	String message;
	String error;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(String message, String error) {
		super();
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
