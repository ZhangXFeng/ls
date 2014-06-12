package com.locationService.exception;

@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception {
	public InvalidPasswordException(String msg){
		super(msg);
	}
}
