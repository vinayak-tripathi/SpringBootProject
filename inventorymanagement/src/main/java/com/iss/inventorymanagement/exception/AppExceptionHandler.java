package com.iss.inventorymanagement.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.iss.inventorymanagement.model.response.ErrorMessage;

import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
		/*
		 * Handles General Exceptions
		*/
		String errorMessageDesc = ex.getLocalizedMessage();
		
		if (errorMessageDesc==null) errorMessageDesc = ex.toString();
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<>(
				errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {NullPointerException.class})
	public ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request){
		/*
		 * Handles the Null Pointer exception. Shows the internal server error as status
		*/
		String errorMessageDesc = ex.getLocalizedMessage();
		
		if (errorMessageDesc==null) errorMessageDesc = ex.toString();
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<>(
				errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = {JwtException.class})
	public ResponseEntity<Object> handleJWTException(Exception ex, WebRequest request){
		/*
		 * Handles the JWTException: Example jwt Expired. Not authorized, etc
		 * Gives http status of unauthorized access
		*/
		String errorMessageDesc = ex.getLocalizedMessage();
		
		if (errorMessageDesc==null) errorMessageDesc = ex.toString();
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<>(
				errorMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = {UserException.class})
	public ResponseEntity<Object> unAuthorizedAction(Exception ex, WebRequest request){
		
		String errorMessageDesc = ex.getLocalizedMessage();
		
		if (errorMessageDesc==null) errorMessageDesc = ex.toString();
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<>(
				errorMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}
	
}
