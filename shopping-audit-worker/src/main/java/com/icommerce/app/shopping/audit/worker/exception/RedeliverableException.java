package com.icommerce.app.shopping.audit.worker.exception;

public class RedeliverableException extends RuntimeException {

	private String body;

	public RedeliverableException(String message, String body) {
		super(message);
		this.body = body;
	}
	public RedeliverableException(String message) {
		super(message);
	}

	public String getBody() {
		return body;
	}
}