package com.locationService.model.outputModel;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * status=0 访问成功</br>
 * status=1 被访问者的用户名和访问密码不匹配</br>
 * status=2 服务器内部错误</br>
 * status=3 输入参数有误
 * @author Administrator
 *
 */
@XmlRootElement
public class LocationAccessOutputModel {
	private int status;
	private String description;
	private String username;
	private long startTime;
	private long endTime;
	private List<LocationInfo> locations;
	
	
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
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



	public List<LocationInfo> getLocations() {
		return locations;
	}



	public void setLocations(List<LocationInfo> locations) {
		this.locations = locations;
	}



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



	@XmlRootElement
	public static class LocationInfo{
		private long updateTime;
		private double longitude;
		private double latitude;
		public long getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(long updateTime) {
			this.updateTime = updateTime;
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
		
	}
}
