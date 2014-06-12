package com.locationService.model.inputModel;

import javax.xml.bind.annotation.XmlRootElement;

import com.locationService.exception.InvalidInputException;

@XmlRootElement
public class LocationAccessInputModel extends BaseInputModel {
	private String visitUserName;
	private String username;
	private String visitPassword;
	private long startTime;
	private long endTime;
	public String getVisitUserName() {
		return visitUserName;
	}
	public void setVisitUserName(String visitUserName) {
		this.visitUserName = visitUserName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getVisitPassword() {
		return visitPassword;
	}
	public void setVisitPassword(String visitPassword) {
		this.visitPassword = visitPassword;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	@Override
	public void checkInput() throws InvalidInputException {
		if(username==null||visitPassword==null||username.equals("")||visitPassword.equals("")){
			throw new InvalidInputException("[invalid input] "+this);
		}
		if(startTime>=endTime){
			throw new InvalidInputException("[invalid input] "+this);
		}
	}
	@Override
	public String toString() {
		return this.visitUserName+","+this.username+","+this.visitPassword+","+this.startTime+","+this.endTime;
	}
}
