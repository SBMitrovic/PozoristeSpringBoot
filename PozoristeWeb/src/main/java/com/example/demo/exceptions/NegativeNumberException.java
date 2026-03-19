package com.example.demo.exceptions;

public class NegativeNumberException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NegativeNumberException(String poruka) {
		super(poruka);
	}

}
