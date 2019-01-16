package com.restaurants.restaurants.db.mariadb.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "login_histories")
public class LoginHistoriesEntity implements Serializable {

	private static final long serialVersionUID = -8739478680411515721L;

	private Long loginHistoryId;
	private RestaurantsEntity restaurantsEntity;
	private LocalDateTime loginDatetime;
	private String ipAddress;
	private String deviceId;
	private String latitudeLongitude;
	private String loginStatus;

	public LoginHistoriesEntity() {
	}

	public LoginHistoriesEntity(Long loginHistoryId, RestaurantsEntity restaurantsEntity, String ipAddress, String deviceId ,
	                            String latitudeLongitude, String loginStatus) {
		this.loginHistoryId = loginHistoryId;
		this.restaurantsEntity = restaurantsEntity;
		this.ipAddress = ipAddress;
		this.deviceId = deviceId;
		this.latitudeLongitude = latitudeLongitude;
		this.loginStatus = loginStatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "login_history_id", unique = true, nullable = false)
	public Long getLoginHistoryId() {
		return this.loginHistoryId;
	}

	public void setLoginHistoryId(Long loginHistoryId) {
		this.loginHistoryId = loginHistoryId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", nullable = false, insertable = true, updatable = true)
	public RestaurantsEntity getRestaurantsEntity() {
		return restaurantsEntity;
	}

	public void setRestaurantsEntity(RestaurantsEntity restaurantsEntity) {
		this.restaurantsEntity = restaurantsEntity;
	}


	@Column(name = "login_datetime", nullable = false)
	public LocalDateTime getLoginDatetime() {
		return loginDatetime;
	}

	public void setLoginDatetime(LocalDateTime loginDatetime) {
		this.loginDatetime = loginDatetime;
	}

	@Column(name = "device_id", nullable = false, length = 128)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "ip_address", nullable = false, length = 128)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "latitude_longitude", nullable = false, length = 128)
	public String getLatitudeLongitude() {
		return latitudeLongitude;
	}

	public void setLatitudeLongitude(String latitudeLongitude) {
		this.latitudeLongitude = latitudeLongitude;
	}

	@Column(name = "login_status", length = 1)
	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}
