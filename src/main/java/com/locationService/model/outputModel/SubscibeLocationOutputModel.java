package com.locationService.model.outputModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * status=0 �ɹ�����</br>
 * status=1 �����������</br>
 * status=2 ����ʧ��,�ڲ��������</br>
 * status=3 �����������
 * @author Administrator
 *
 */
@XmlRootElement
public class SubscibeLocationOutputModel {
	private int status;
	private String description;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
