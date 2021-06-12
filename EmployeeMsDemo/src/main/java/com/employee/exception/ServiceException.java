package com.employee.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public ServiceException(String message) {
		this.message = message;
	}

	public String toString() {
		return ("ServiceException Occurred: " + message);
	}
}
