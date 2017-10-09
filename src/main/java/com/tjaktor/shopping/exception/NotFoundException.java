package com.tjaktor.shopping.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1229553750111736656L;
	
	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}