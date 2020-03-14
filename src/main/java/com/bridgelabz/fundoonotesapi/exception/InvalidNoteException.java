package com.bridgelabz.fundoonotesapi.exception;

public class InvalidNoteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidNoteException(String message) {
		super(message);
	}
}
