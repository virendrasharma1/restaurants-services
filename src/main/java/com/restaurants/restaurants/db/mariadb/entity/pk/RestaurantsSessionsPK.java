package com.restaurants.restaurants.db.mariadb.entity.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RestaurantsSessionsPK implements Serializable {

	private static final long serialVersionUID = 7181436844744152053L;

	private Long restaurantId;
	private String deviceId;

	public RestaurantsSessionsPK() {
	}

	public RestaurantsSessionsPK(Long restaurantId, String deviceId) {
		this.restaurantId = restaurantId;
		this.deviceId = deviceId;
	}

	@Column(name = "restaurant_id", nullable = false)
	public Long getLoginId() {
		return this.restaurantId;
	}

	public void setLoginId(Long loginId) {
		this.restaurantId = loginId;
	}

	@Column(name = "device_id", nullable = false, length = 128)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RestaurantsSessionsPK))
			return false;
		RestaurantsSessionsPK castOther = (RestaurantsSessionsPK) other;

		return (this.getLoginId() == castOther.getLoginId())
				&& ((this.getDeviceId() == castOther.getDeviceId()) || (this.getDeviceId() != null
						&& castOther.getDeviceId() != null && this.getDeviceId().equals(castOther.getDeviceId())));
	}

	public int hashCode() {
		Integer result = 17;

		result = 37 * result + this.getLoginId().intValue();
		result = 37 * result + (getDeviceId() == null ? 0 : this.getDeviceId().hashCode());
		return result;
	}
}
