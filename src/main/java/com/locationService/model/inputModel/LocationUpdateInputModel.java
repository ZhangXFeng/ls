package com.locationService.model.inputModel;

import javax.xml.bind.annotation.XmlRootElement;

import com.locationService.exception.InvalidInputException;

@XmlRootElement
public class LocationUpdateInputModel extends BaseInputModel {
	private long updateTime;
	private String username;
	private double longitude;
	private double latitude;
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "[locationUpdateInput] "+this.username+","+this.updateTime+","+this.longitude+","+this.latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Override
	public void checkInput() throws InvalidInputException {
		if(username==null||username.equals("")){
			throw new InvalidInputException("username="+username);
		}
	}
}
