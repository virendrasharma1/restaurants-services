package com.restaurants.bo;

import java.io.Serializable;
import java.util.List;

public class RestaurantOrdersBO implements Serializable{

	private String orderId;
	private String restaurantId;
	private String totalCost;
	private String orderedTime;
	private List<OrderDetailsBO> orderDetailsBOS;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public List<OrderDetailsBO> getOrderDetailsBOS() {
		return orderDetailsBOS;
	}

	public void setOrderDetailsBOS(List<OrderDetailsBO> orderDetailsBOS) {
		this.orderDetailsBOS = orderDetailsBOS;
	}

	public String getOrderedTime() {
		return orderedTime;
	}

	public void setOrderedTime(String orderedTime) {
		this.orderedTime = orderedTime;
	}
}
