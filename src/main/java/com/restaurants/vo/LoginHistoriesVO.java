package com.restaurants.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LoginHistoriesVO implements Serializable {

	private static final long serialVersionUID = 1495374060731399551L;

	private Long loginId;
	private LocalDateTime loginDatetime;
	private String ipAddress;
	private String deviceId;
	private String latitudeLongitude;
	private String loginStatus;


	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public LocalDateTime getLoginDatetime() {
		return loginDatetime;
	}

	public void setLoginDatetime(LocalDateTime loginDatetime) {
		this.loginDatetime = loginDatetime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getLatitudeLongitude() {
		return latitudeLongitude;
	}

	public void setLatitudeLongitude(String latitudeLongitude) {
		this.latitudeLongitude = latitudeLongitude;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}
