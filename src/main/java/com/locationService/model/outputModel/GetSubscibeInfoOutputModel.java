package com.locationService.model.outputModel;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetSubscibeInfoOutputModel {
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


	private String username;
	private List<SubscibeInfo> subscibeInfos;
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public List<SubscibeInfo> getSubscibeInfos() {
		return subscibeInfos;
	}


	public void setSubscibeInfos(List<SubscibeInfo> subscibeInfos) {
		this.subscibeInfos = subscibeInfos;
	}

	@XmlRootElement
	public static class SubscibeInfo{
		private String target;
		private double longitude;
		private double latitude;
		private int near;
		public String getTarget() {
			return target;
		}
		public void setTarget(String target) {
			this.target = target;
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
}
