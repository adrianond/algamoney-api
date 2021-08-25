package com.algamoney.api.usecase.avenger.exception;

public class AvengerNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AvengerNotFoundException() {
		super();
	}

	public AvengerNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AvengerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AvengerNotFoundException(String message) {
		super(message);
	}

	public AvengerNotFoundException(Throwable cause) {
		super(cause);
	}

}
