package com.locationService.model.outputModel;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * status=0 登录成功</br>
 * status=1 用户名或密码错误</br>
 * status=2 服务器内部错误</br>
 * status=3 输入参数有误
 * @author Administrator
 *
 */
@XmlRootElement
public class LoginOutputModel {
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
