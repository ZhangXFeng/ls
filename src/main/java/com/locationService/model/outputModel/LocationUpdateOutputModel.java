package com.locationService.model.outputModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * status=0 �ɹ�����</br>
 * status=3 �����������
 * @author Administrator
 *
 */
@XmlRootElement
public class LocationUpdateOutputModel {
	private int status;
	private String description;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
