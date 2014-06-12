package com.locationService.exception;

public class UsernameOrPasswordError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameOrPasswordError(String msg){
		super(msg);
	}
}
