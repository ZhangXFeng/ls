package com.locationService.model.inputModel;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.locationService.exception.InvalidInputException;

@XmlRootElement
public class CancleSubscibeInputModel extends BaseInputModel {

	private String username;
	private String target;
	private List<LocationInfo> locationInfos;

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

	@Override
	public void checkInput() throws InvalidInputException {

		if (this.username == null || target == null || username.equals("")
				|| target.equals("")) {
			throw new InvalidInputException("invalid input");
		}
	}

	public List<LocationInfo> getLocationInfos() {
		return locationInfos;
	}

	public void setLocationInfos(List<LocationInfo> locationInfos) {
		this.locationInfos = locationInfos;
	}

	@XmlRootElement
	public static class LocationInfo {
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
