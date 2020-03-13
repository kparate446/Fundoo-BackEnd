package com.bridgelabz.fandoonotesapi.exception;

public class InvalidReminderException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidReminderException(String message) {
		super(message);
	}
}
