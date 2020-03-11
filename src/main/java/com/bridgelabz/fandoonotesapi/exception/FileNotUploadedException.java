package com.bridgelabz.fandoonotesapi.exception;

public class FileNotUploadedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public FileNotUploadedException(String message) {
		super(message);
	}
}
