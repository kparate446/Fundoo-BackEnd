package com.bridgelabz.fundoonotesapi.exception;

public class FileIsEmpty extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public FileIsEmpty(String message) {
		super(message);
	}
}
