package com.locationService.model.outputModel;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * status=0 成功注册</br>
 * status=1 已经存在</br>
 * status=2 注册失败,内部服务错误</br>
 * status=3 输入参数有误
 * @author Administrator
 *
 */
@XmlRootElement
public class RegisterOutputModel {
	
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
