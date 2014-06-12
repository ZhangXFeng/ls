package com.locationService.model.inputModel;

import javax.xml.bind.annotation.XmlRootElement;

import com.locationService.exception.InvalidInputException;
import com.locationService.exception.UsernameOrPasswordIsNuLLException;

@XmlRootElement
public class LoginInputModel extends BaseInputModel{
	private String username;
	private String password;
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
	@Override
	public void checkInput() throws InvalidInputException {
		if(username==null||password==null||username.equals("")||password.equals("")){
			throw new InvalidInputException("[invalid input] "+this);
		}
	}
	@Override
	public String toString() {
		return this.username+","+this.password;
	}
	public String getRegistrationID() {
		return registrationID;
	}
	public void setRegistrationID(String registrationID) {
		this.registrationID = registrationID;
	}
}
