package com.restaurants.db.mariadb.entity;
import java.time.LocalDateTime;
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
	private String passwordEncrypted;
	private String activeOrNot;
	private LocalDateTime createdTimestamp;
	private Long createdBy;
	private Set<RestaurantSessionsEntity> restaurantSessionsEntity = new HashSet<>(0);
	private Set<RestaurantItemsEntity> restaurantItemsEntity = new HashSet<>(0);

	public RestaurantsEntity() {
	}

	public RestaurantsEntity(String email, String phone, String name, String passwordEncrypted, String activeOrNot, Set<RestaurantSessionsEntity> restaurantSessionsEntity, LocalDateTime createdTimestamp, Long createdBy) {
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.passwordEncrypted = passwordEncrypted;
		this.activeOrNot = activeOrNot;
		this.restaurantSessionsEntity = restaurantSessionsEntity;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
	}

	public RestaurantsEntity(String email, String phone, String name, String address,
			String passwordEncrypted, String activeOrNot, Set<RestaurantSessionsEntity> restaurantSessionsEntity, LocalDateTime createdTimestamp, Long createdBy) {
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.address = address;
		this.passwordEncrypted = passwordEncrypted;
		this.activeOrNot = activeOrNot;
        this.restaurantSessionsEntity = restaurantSessionsEntity;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
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

	@Column(name = "email", nullable = true, length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", nullable = true, length = 16)
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

	@Column(name = "created_timestamp", nullable = false, length = 19)
	public LocalDateTime getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Column(name = "created_by", nullable = false)
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantsEntity", cascade = CascadeType.REMOVE)
    public Set<RestaurantSessionsEntity> getRestaurantSessionsEntity() {
        return restaurantSessionsEntity;
    }

    public void setRestaurantSessionsEntity(Set<RestaurantSessionsEntity> restaurantSessionsEntity) {
        this.restaurantSessionsEntity = restaurantSessionsEntity;
    }

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants", cascade = CascadeType.REMOVE)
	public Set<RestaurantItemsEntity> getRestaurantItemsEntity() {
		return restaurantItemsEntity;
	}

	public void setRestaurantItemsEntity(Set<RestaurantItemsEntity> restaurantItemsEntity) {
		this.restaurantItemsEntity = restaurantItemsEntity;
	}
}
