package com.restaurants.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class RestaurantOrdersVO implements Serializable{

	private Long orderId;
	private Long restaurantId;
	private Double totalCost;
	private LocalDateTime orderedTime;
	private List<OrderDetailsVO> orderDetailsVOS;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public List<OrderDetailsVO> getOrderDetailsVOS() {
		return orderDetailsVOS;
	}

	public void setOrderDetailsVOS(List<OrderDetailsVO> orderDetailsVOS) {
		this.orderDetailsVOS = orderDetailsVOS;
	}

	public LocalDateTime getOrderedTime() {
		return orderedTime;
	}

	public void setOrderedTime(LocalDateTime orderedTime) {
		this.orderedTime = orderedTime;
	}
}
