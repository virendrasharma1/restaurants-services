package com.restaurants.restaurants.db.mariadb.entity;


import com.restaurants.restaurants.db.mariadb.entity.pk.RestaurantsSessionsPK;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "restaurant_sessions")
public class RestaurantSessionsEntity implements Serializable {

	private static final long serialVersionUID = 4020873973125365740L;

	private RestaurantsSessionsPK id;
	private RestaurantsEntity restaurantsEntity;
	private String sessionToken;
	private String gcmToken;
	private LocalDateTime loginDatetime;
	private LocalDateTime logoutOrInvalidationDatetime;
	private String status;

	public RestaurantSessionsEntity() {
	}

	public RestaurantSessionsEntity(RestaurantsSessionsPK id, RestaurantsEntity restaurantsEntity, String sessionToken, LocalDateTime loginDatetime,
	                                LocalDateTime logoutOrInvalidationDatetime, String status) {
		this.id = id;
		this.restaurantsEntity = restaurantsEntity;
		this.sessionToken = sessionToken;
		this.loginDatetime = loginDatetime;
		this.logoutOrInvalidationDatetime = logoutOrInvalidationDatetime;
		this.status = status;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "restaurantId", column = @Column(name = "restaurant_id", nullable = false) ),
			@AttributeOverride(name = "deviceId", column = @Column(name = "device_id", nullable = false, length = 128) ) })
	public RestaurantsSessionsPK getId() {
		return this.id;
	}

	public void setId(RestaurantsSessionsPK id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
	public RestaurantsEntity getRestaurantsEntity() {
		return restaurantsEntity;
	}

	public void setRestaurantsEntity(RestaurantsEntity restaurantsEntity) {
		this.restaurantsEntity = restaurantsEntity;
	}



	@Column(name = "session_token", nullable = false, length = 128)
	public String getSessionToken() {
		return this.sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	@Column(name = "gcm_token", length = 256)
	public String getGcmToken() {
		return this.gcmToken;
	}

	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}

	@Column(name = "login_datetime", nullable = false, length = 0)
	public LocalDateTime getLoginDatetime() {
		return this.loginDatetime;
	}

	public void setLoginDatetime(LocalDateTime loginDatetime) {
		this.loginDatetime = loginDatetime;
	}

	@Column(name = "logout_or_invalidation_datetime", nullable = false, length = 0)
	public LocalDateTime getLogoutOrInvalidationDatetime() {
		return this.logoutOrInvalidationDatetime;
	}

	public void setLogoutOrInvalidationDatetime(LocalDateTime logoutOrInvalidationDatetime) {
		this.logoutOrInvalidationDatetime = logoutOrInvalidationDatetime;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
