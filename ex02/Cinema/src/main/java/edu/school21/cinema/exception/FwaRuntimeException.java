package edu.school21.cinema.exception;

import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class FwaRuntimeException extends RuntimeException {

	private Map<String, String> errors;
	private int statusCode;

	public FwaRuntimeException(Map<String, String> errors) {
		this.errors = errors;
		this.statusCode = SC_BAD_REQUEST;
	}

	public FwaRuntimeException(String message, int statusCode) {
		super(message);
		this.errors = new HashMap<>();
		this.errors.put("commonError", message);
		this.statusCode = statusCode;
	}

	public FwaRuntimeException(String message, int statusCode, Throwable cause) {
		super(message, cause);
		this.errors = new HashMap<>();
		this.errors.put("commonError", message);
		this.statusCode = statusCode;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public FwaRuntimeException(Throwable cause) {
		super(cause);
	}
}
