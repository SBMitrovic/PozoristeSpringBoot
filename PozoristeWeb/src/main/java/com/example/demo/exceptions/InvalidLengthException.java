package com.example.demo.exceptions;

public class InvalidLengthException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidLengthException(String poruka, Integer len) {
		super(poruka+" Trenutna dužina: "+len);
	}
	
}
