package com.restaurants.restaurants.vo.Ids;


import java.io.Serializable;

public class RestaurantsSessionsId implements Serializable {

	private static final long serialVersionUID = 5882612021143834895L;

	private Long loginId;
	private String deviceId;

	public RestaurantsSessionsId() {
	}

	public RestaurantsSessionsId(Long loginId, String deviceId) {
		this.loginId = loginId;
		this.deviceId = deviceId;
	}

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
}
