package com.locationService.model.inputModel;

import javax.xml.bind.annotation.XmlRootElement;

import com.locationService.exception.InvalidInputException;
import com.locationService.exception.InvalidPasswordException;
import com.locationService.exception.UsernameOrPasswordIsNuLLException;

@XmlRootElement
public class RegisterInputModel extends BaseInputModel{

	private String username;
	private String password;
	
	private String visitPassword;

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
	public void checkInput() throws InvalidInputException {
		if(username==null||password==null||username.equals("")||password.equals("")){
			throw new InvalidInputException("[invalid input] "+this);
		}
		if(password.length()<8){
			throw new InvalidInputException("[invalid input] "+this);
		}
	}
	@Override
	public String toString() {
		return "[registerInput] "+this.username+","+this.password+","+this.visitPassword;
	}

}
