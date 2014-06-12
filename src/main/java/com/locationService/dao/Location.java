package com.locationService.dao;

import java.sql.Date;

public class Location {
	private String username;
	private long updatetime;
	private double longitude;
	private double latitude;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
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
	public String toString() {
		return this.username+","+new Date(this.updatetime)+","+this.longitude+","+this.latitude;
	}
}
