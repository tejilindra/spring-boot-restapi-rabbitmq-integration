package com.tilindra.demo.exception;

public class DataFormatException extends RuntimeException {

	public DataFormatException() {
		super();
	}

	public DataFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataFormatException(String message) {
		super(message);
	}

	public DataFormatException(Throwable cause) {
		super(cause);
	}

}
