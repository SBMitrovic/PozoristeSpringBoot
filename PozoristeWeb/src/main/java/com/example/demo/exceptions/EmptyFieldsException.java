package com.example.demo.exceptions;

public class EmptyFieldsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmptyFieldsException(String poruka) {
		super(poruka);
	}
	
}
