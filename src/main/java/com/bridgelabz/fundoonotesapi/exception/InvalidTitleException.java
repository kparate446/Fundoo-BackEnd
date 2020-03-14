package com.bridgelabz.fundoonotesapi.exception;

public class InvalidTitleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidTitleException(String message) {
		super(message);
	}
}
