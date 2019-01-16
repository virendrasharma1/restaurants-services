package com.restaurants.restaurants.db.mariadb.entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "restaurants")
public class RestaurantsEntity implements java.io.Serializable {

	private Long restaurantId;
	private String email;
	private String phone;
	private String name;
	private String address;
	private String cityId;
	private String stateId;
	private String passwordEncrypted;
	private String activeOrNot;
	private String type;
	private Date createdTimestamp;
	private long createdBy;
	private Date lastUpdatedTimstamp;
	private long lastUpdatedBy;
	private Set<RestaurantSessionsEntity> restaurantSessionsEntity = new HashSet<>(0);

	public RestaurantsEntity() {
	}

	public RestaurantsEntity(String email, String phone, String name, String passwordEncrypted, String activeOrNot,
			String type, Set<RestaurantSessionsEntity> restaurantSessionsEntity, Date createdTimestamp, long createdBy, Date lastUpdatedTimstamp, long lastUpdatedBy) {
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.passwordEncrypted = passwordEncrypted;
		this.activeOrNot = activeOrNot;
		this.type = type;
		this.restaurantSessionsEntity = restaurantSessionsEntity;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.lastUpdatedTimstamp = lastUpdatedTimstamp;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public RestaurantsEntity(String email, String phone, String name, String address, String cityId, String stateId,
			String passwordEncrypted, String activeOrNot, String type, Set<RestaurantSessionsEntity> restaurantSessionsEntity, Date createdTimestamp, long createdBy,
			Date lastUpdatedTimstamp, long lastUpdatedBy) {
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.address = address;
		this.cityId = cityId;
		this.stateId = stateId;
		this.passwordEncrypted = passwordEncrypted;
		this.activeOrNot = activeOrNot;
		this.type = type;
        this.restaurantSessionsEntity = restaurantSessionsEntity;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.lastUpdatedTimstamp = lastUpdatedTimstamp;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "restaurant_id", unique = true, nullable = false)
	public Long getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Column(name = "email", nullable = false, length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", nullable = false, length = 16)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "name", nullable = false, length = 256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", length = 256)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city_id", length = 6)
	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Column(name = "state_id", length = 6)
	public String getStateId() {
		return this.stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	@Column(name = "password_encrypted", nullable = false, length = 256)
	public String getPasswordEncrypted() {
		return this.passwordEncrypted;
	}

	public void setPasswordEncrypted(String passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}

	@Column(name = "active_or_not", nullable = false, length = 1)
	public String getActiveOrNot() {
		return this.activeOrNot;
	}

	public void setActiveOrNot(String activeOrNot) {
		this.activeOrNot = activeOrNot;
	}

	@Column(name = "type", nullable = false, length = 2)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_timestamp", nullable = false, length = 19)
	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Column(name = "created_by", nullable = false)
	public long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_timstamp", nullable = false, length = 19)
	public Date getLastUpdatedTimstamp() {
		return this.lastUpdatedTimstamp;
	}

	public void setLastUpdatedTimstamp(Date lastUpdatedTimstamp) {
		this.lastUpdatedTimstamp = lastUpdatedTimstamp;
	}

	@Column(name = "last_updated_by", nullable = false)
	public long getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantsEntity", cascade = CascadeType.REMOVE)
    public Set<RestaurantSessionsEntity> getRestaurantSessionsEntity() {
        return restaurantSessionsEntity;
    }

    public void setRestaurantSessionsEntity(Set<RestaurantSessionsEntity> restaurantSessionsEntity) {
        this.restaurantSessionsEntity = restaurantSessionsEntity;
    }
}
