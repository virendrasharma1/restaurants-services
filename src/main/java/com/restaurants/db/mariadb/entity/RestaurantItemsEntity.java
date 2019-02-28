package com.restaurants.db.mariadb.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "restaurant_items")
public class RestaurantItemsEntity implements java.io.Serializable {

	private Long itemId;
	private RestaurantsEntity restaurants;
	private String itemName;
	private double itemCost;
	private long createdBy;
	private Date createdTimestamp;
	private Set<OrderDetailsEntity> OrderDetailsEntityes = new HashSet<OrderDetailsEntity>(0);

	public RestaurantItemsEntity() {
	}

	public RestaurantItemsEntity(RestaurantsEntity restaurants, String itemName, double itemCost, long createdBy,
                                 Date createdTimestamp) {
		this.restaurants = restaurants;
		this.itemName = itemName;
		this.itemCost = itemCost;
		this.createdBy = createdBy;
		this.createdTimestamp = createdTimestamp;
	}

	public RestaurantItemsEntity(RestaurantsEntity restaurants, String itemName, double itemCost, long createdBy,
                                 Date createdTimestamp, Set<OrderDetailsEntity> OrderDetailsEntityes) {
		this.restaurants = restaurants;
		this.itemName = itemName;
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

	@Column(name = "item_name", nullable = false, length = 128)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "item_cost", nullable = false, precision = 13, scale = 3)
	public double getItemCost() {
		return this.itemCost;
	}

	public void setItemCost(double itemCost) {
		this.itemCost = itemCost;
	}

	@Column(name = "created_by", nullable = false)
	public long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_timestamp", nullable = false, length = 19)
	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantItems")
	public Set<OrderDetailsEntity> getOrderDetailsEntityes() {
		return this.OrderDetailsEntityes;
	}

	public void setOrderDetailsEntityes(Set<OrderDetailsEntity> OrderDetailsEntityes) {
		this.OrderDetailsEntityes = OrderDetailsEntityes;
	}

}
