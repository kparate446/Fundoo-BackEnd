package com.bridgelabz.fandoonotesapi.exception;

public class ReceiverMailAlreadyPresentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ReceiverMailAlreadyPresentException(String message) {
		super(message);
	}
}
