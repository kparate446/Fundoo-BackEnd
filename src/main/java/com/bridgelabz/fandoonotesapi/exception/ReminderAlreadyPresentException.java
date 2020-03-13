package com.bridgelabz.fandoonotesapi.exception;

public class ReminderAlreadyPresentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ReminderAlreadyPresentException(String message) {
		super(message);
	}
}
