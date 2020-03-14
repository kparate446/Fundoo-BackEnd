package com.bridgelabz.fundoonotesapi.exception;

public class LabelAlreadyPresentException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public LabelAlreadyPresentException(String message) {
		super(message);
	}
}
