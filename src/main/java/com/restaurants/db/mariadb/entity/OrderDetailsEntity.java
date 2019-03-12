package com.restaurants.db.mariadb.entity;

import com.restaurants.db.mariadb.entity.pk.OrderDetailsPK;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "order_details")
public class OrderDetailsEntity implements java.io.Serializable {

	private OrderDetailsPK id;
	private RestaurantItemsEntity restaurantItems;
	private RestaurantOrdersEntity restaurantOrders;
	private Integer numberOfItems;
	private Double totalCost;
	private LocalDateTime createdTimestamp;

	public OrderDetailsEntity() {
	}

	public OrderDetailsEntity(OrderDetailsPK id, RestaurantItemsEntity restaurantItems, RestaurantOrdersEntity restaurantOrders,
							  LocalDateTime createdTimestamp) {
		this.id = id;
		this.restaurantItems = restaurantItems;
		this.restaurantOrders = restaurantOrders;
		this.createdTimestamp = createdTimestamp;
	}

	public OrderDetailsEntity(OrderDetailsPK id, RestaurantItemsEntity restaurantItems, RestaurantOrdersEntity restaurantOrders,
                              Integer numberOfItems, Double totalCost, LocalDateTime createdTimestamp) {
		this.id = id;
		this.restaurantItems = restaurantItems;
		this.restaurantOrders = restaurantOrders;
		this.numberOfItems = numberOfItems;
		this.totalCost = totalCost;
		this.createdTimestamp = createdTimestamp;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false)),
			@AttributeOverride(name = "itemId", column = @Column(name = "item_id", nullable = false)) })
	public OrderDetailsPK getId() {
		return this.id;
	}

	public void setId(OrderDetailsPK id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
	public RestaurantItemsEntity getRestaurantItems() {
		return this.restaurantItems;
	}

	public void setRestaurantItems(RestaurantItemsEntity restaurantItems) {
		this.restaurantItems = restaurantItems;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
	public RestaurantOrdersEntity getRestaurantOrders() {
		return this.restaurantOrders;
	}

	public void setRestaurantOrders(RestaurantOrdersEntity restaurantOrders) {
		this.restaurantOrders = restaurantOrders;
	}

	@Column(name = "number_of_items")
	public Integer getNumberOfItems() {
		return this.numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	@Column(name = "total_cost", precision = 13, scale = 3)
	public Double getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	@Column(name = "created_timestamp", nullable = false, length = 19)
	public LocalDateTime getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

}
