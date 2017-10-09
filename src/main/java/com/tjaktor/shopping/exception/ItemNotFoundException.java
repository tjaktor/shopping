package com.tjaktor.shopping.exception;

public class ItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6940763846093569852L;

	public ItemNotFoundException() {
		super();
	}
	
	public ItemNotFoundException(String message) {
		super(message);
	}
	
	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
