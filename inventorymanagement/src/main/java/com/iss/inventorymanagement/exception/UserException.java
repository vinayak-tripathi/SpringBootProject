package com.iss.inventorymanagement.exception;

public class UserException extends RuntimeException{
	
	/*
	 * Class for creating cutom error message for the user
	 */
	private static final long serialVersionUID = 1L;

	public UserException(String message) {
		super(message);
	}
}
