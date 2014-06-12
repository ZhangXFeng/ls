package com.locationService.dao;

public class User {
	private String username;
	private String password;
	private String visitPassword;
	private String registrationID;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVisitPassword() {
		return visitPassword;
	}
	public void setVisitPassword(String visitPassword) {
		this.visitPassword = visitPassword;
	}
	@Override
	public String toString() {
		return this.username;
	}
	public String getRegistrationID() {
		return registrationID;
	}
	public void setRegistrationID(String registrationID) {
		this.registrationID = registrationID;
	}
}
