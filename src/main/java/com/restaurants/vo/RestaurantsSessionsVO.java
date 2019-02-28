package com.restaurants.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RestaurantsSessionsVO implements Serializable {

	private static final long serialVersionUID = -2308607126767130206L;

	private Long loginId;
	private String deviceId;
	private String sessionToken;
	private String gcmToken;
	private LocalDateTime loginDatetime;
    private LocalDateTime logoutOrInvalidationDatetime;
	private String status;
	
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getGcmToken() {
		return gcmToken;
	}

	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}

	public LocalDateTime getLoginDatetime() {
		return loginDatetime;
	}

	public void setLoginDatetime(LocalDateTime loginDatetime) {
		this.loginDatetime = loginDatetime;
	}

	public LocalDateTime getLogoutOrInvalidationDatetime() {
		return logoutOrInvalidationDatetime;
	}

	public void setLogoutOrInvalidationDatetime(LocalDateTime logoutOrInvalidationDatetime) {
		this.logoutOrInvalidationDatetime = logoutOrInvalidationDatetime;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
