package com.locationService.model.inputModel;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SubscibeLocationInputModel extends BaseInputModel {
	private String username;
	private String target;
	private String visitPassword;
	private List<LocationInfo> targetlocations;
	
	
	
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



	public String getVisitPassword() {
		return visitPassword;
	}



	public void setVisitPassword(String visitPassword) {
		this.visitPassword = visitPassword;
	}



	public List<LocationInfo> getTargetlocations() {
		return targetlocations;
	}



	public void setTargetlocations(List<LocationInfo> targetlocations) {
		this.targetlocations = targetlocations;
	}



	@XmlRootElement
	public static class LocationInfo{
		private double longitude;
		private double latitude;
		private int near;
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
}
