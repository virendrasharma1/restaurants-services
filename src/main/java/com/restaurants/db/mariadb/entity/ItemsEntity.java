package com.restaurants.db.mariadb.entity;


import com.restaurants.db.mariadb.entity.pk.ItemsPK;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "items")
public class ItemsEntity implements java.io.Serializable {

	private ItemsPK id;
	private LocalDateTime createdTimestamp;
	private Set<RestaurantItemsEntity> restaurantItemsEntities = new HashSet<RestaurantItemsEntity>(0);

	public ItemsEntity() {
	}

	public ItemsEntity(ItemsPK id, LocalDateTime createdTimestamp) {
		this.id = id;
		this.createdTimestamp = createdTimestamp;
	}

	public ItemsEntity(LocalDateTime createdTimestamp, ItemsPK id) {
		this.id = id;
		this.createdTimestamp = createdTimestamp;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "itemName", column = @Column(name = "item_name", nullable = false)),
			@AttributeOverride(name = "itemType", column = @Column(name = "item_type", nullable = false)) })
	public ItemsPK getId() {
		return id;
	}

	public void setId(ItemsPK id) {
		this.id = id;
	}

	@Column(name = "created_timestamp", nullable = false, length = 19)
	public LocalDateTime getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemsEntity", cascade = CascadeType.REMOVE)
	public Set<RestaurantItemsEntity> getRestaurantItemsEntities() {
		return restaurantItemsEntities;
	}

	public void setRestaurantItemsEntities(Set<RestaurantItemsEntity> restaurantItemsEntities) {
		this.restaurantItemsEntities = restaurantItemsEntities;
	}
}
