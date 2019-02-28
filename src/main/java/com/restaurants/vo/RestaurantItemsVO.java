package com.restaurants.vo;

import java.io.Serializable;
import java.util.List;

public class RestaurantItemsVO implements Serializable{

	private Long orderId;
	private Long itemId;
	private Long restaurantId;
	private Double totalCost;
	private List<OrderDetailsVO> orderDetailsVOS;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
}
