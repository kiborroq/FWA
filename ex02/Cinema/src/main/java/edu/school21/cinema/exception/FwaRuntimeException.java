package edu.school21.cinema.exception;

import java.util.HashMap;
import java.util.Map;

public class FwaRuntimeException extends RuntimeException {

	private Map<String, String> errors;

	public FwaRuntimeException() {
		errors = new HashMap<>();
	}

	public FwaRuntimeException(Map<String, String> errors) {
		this.errors = errors;
	}

	public FwaRuntimeException(String message) {
		super(message);
		this.errors = new HashMap<>();
		this.errors.put("commonError", message);
	}

	public FwaRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.errors = new HashMap<>();
		this.errors.put("commonError", message);
	}

	public FwaRuntimeException(Throwable cause) {
		super(cause);
		this.errors = new HashMap<>();
	}

	public FwaRuntimeException(Map<String, String> errors, Throwable cause) {
		super(cause);
		this.errors = errors;
	}

	public FwaRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.errors = new HashMap<>();
		this.errors.put("commonError", message);
	}

	public Map<String, String> getErrors() {
		return errors;
	}
}
