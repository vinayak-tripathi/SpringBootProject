package com.iss.inventorymanagement.exception;

public class ProductException extends RuntimeException {

	/*
	 * Class for creating cutom error message for the Products 
	 */
	private static final long serialVersionUID = 1L;

	public ProductException(String message) {
		super(message);
	}

}
