package com.locationService.dao;

public class Subscibe {
	private String username;
	private String target;
	private String visitPasssword;
	private double longitude;
	private double latitude;
	private int near;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getVisitPasssword() {
		return visitPasssword;
	}
	public void setVisitPasssword(String visitPasssword) {
		this.visitPasssword = visitPasssword;
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
	public int getNear() {
		return near;
	}
	public void setNear(int near) {
		this.near = near;
	}
	
}
