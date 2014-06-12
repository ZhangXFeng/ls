package com.locationService.model.outputModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseReturnModel {
	private boolean isSuccess;
	private String description;
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
