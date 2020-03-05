package com.bridgelabz.user.exception;

public class InvalidPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidPasswordException(String messege) {
		super(messege);
	}
}
