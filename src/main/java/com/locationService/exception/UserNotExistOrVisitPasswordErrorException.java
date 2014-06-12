package com.locationService.exception;

@SuppressWarnings("serial")
public class UserNotExistOrVisitPasswordErrorException extends Exception {

	public UserNotExistOrVisitPasswordErrorException(String msg){
		super(msg);
	}
}
