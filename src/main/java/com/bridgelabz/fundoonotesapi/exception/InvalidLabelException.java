package com.bridgelabz.fundoonotesapi.exception;

public class InvalidLabelException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidLabelException(String message) {
		super(message);
	}
}
