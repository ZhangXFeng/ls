package com.locationService.model.inputModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetSubscibeInfoInputModel extends BaseInputModel {

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
