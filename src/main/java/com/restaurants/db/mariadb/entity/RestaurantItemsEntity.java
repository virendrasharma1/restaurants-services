package com.restaurants.db.mariadb.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "restaurant_items")
public class RestaurantItemsEntity implements java.io.Serializable {

	private Long itemId;
	private RestaurantsEntity restaurants;
	private ItemsEntity itemsEntity;
	private Double itemCost;
	private Long createdBy;
	private LocalDateTime createdTimestamp;
	private Set<OrderDetailsEntity> OrderDetailsEntityes = new HashSet<OrderDetailsEntity>(0);

	public RestaurantItemsEntity() {
	}

	public RestaurantItemsEntity(RestaurantsEntity restaurants, ItemsEntity itemsEntity, Double itemCost, Long createdBy,
								 LocalDateTime createdTimestamp) {
		this.restaurants = restaurants;
		this.itemsEntity = itemsEntity;
		this.itemCost = itemCost;
		this.createdBy = createdBy;
		this.createdTimestamp = createdTimestamp;
	}

	public RestaurantItemsEntity(RestaurantsEntity restaurants, ItemsEntity itemsEntity, Double itemCost, Long createdBy,
								 LocalDateTime createdTimestamp, Set<OrderDetailsEntity> OrderDetailsEntityes) {
		this.restaurants = restaurants;
		this.itemsEntity = itemsEntity;
		this.itemCost = itemCost;
		this.createdBy = createdBy;
		this.createdTimestamp = createdTimestamp;
		this.OrderDetailsEntityes = OrderDetailsEntityes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "item_id", unique = true, nullable = false)
	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", nullable = false)
	public RestaurantsEntity getRestaurants() {
		return this.restaurants;
	}

	public void setRestaurants(RestaurantsEntity restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "item_cost", nullable = false, precision = 13, scale = 3)
	public Double getItemCost() {
		return this.itemCost;
	}

	public void setItemCost(Double itemCost) {
		this.itemCost = itemCost;
	}

	@Column(name = "created_by", nullable = false)
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_timestamp", nullable = false, length = 19)
	public LocalDateTime getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantItems")
	public Set<OrderDetailsEntity> getOrderDetailsEntityes() {
		return this.OrderDetailsEntityes;
	}

	public void setOrderDetailsEntityes(Set<OrderDetailsEntity> OrderDetailsEntityes) {
		this.OrderDetailsEntityes = OrderDetailsEntityes;
	}

	@ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumns({
			@JoinColumn(name = "item_name", referencedColumnName = "item_name"),
			@JoinColumn(name = "item_type", referencedColumnName = "item_type")})
	public ItemsEntity getItemsEntity() {
		return itemsEntity;
	}

	public void setItemsEntity(ItemsEntity itemsEntity) {
		this.itemsEntity = itemsEntity;
	}
}
