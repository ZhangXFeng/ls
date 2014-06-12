package com.locationService.client;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;


public interface LocationServiceInterface {

	public JSONObject register(String username,String password,String visitPassword);
	public JSONObject login(String username,String password,String registrationID);
	public JSONObject updateLocation(long updateTime,String username,double longitude,double latitude);
	public JSONObject subscibeLocation(String username,String target,String visitPassword,List<LocationWithNear> locationWithNears);
	public JSONObject accessLocation(String visitUserName,String username,String visitPassword,long startTime,long endTime);
	public JSONObject cancleSubscibe(String username,String target,List<Location> locations);
	public JSONObject getSubscibeInfo(String username);
	
	public static class LocationWithNear{
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
	public static class Location{
		private double longitude;
		private double latitude;
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
		
	}
}
