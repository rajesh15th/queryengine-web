package com.randish.exceptions;

public class QueryEngineException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public QueryEngineException() {
		super();
	}
	public QueryEngineException(String message) {
		super(message);
	}
	public QueryEngineException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
