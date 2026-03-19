package com.example.demo.exceptions;

public class SavingErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SavingErrorException(String poruka) {
		super(poruka);
	}
	
}
