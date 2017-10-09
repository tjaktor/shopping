package com.tjaktor.shopping.exception;

public class CartServiceException extends RuntimeException {

	private static final long serialVersionUID = 6669573198272299170L;

	public CartServiceException() {
		super();
	}

	public CartServiceException(String message) {
		super(message);
	}

	public CartServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
