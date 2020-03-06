package com.bridgelabz.fandoonotesapi.exception;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidTokenException(String messege) {
		super(messege);
	}
}
