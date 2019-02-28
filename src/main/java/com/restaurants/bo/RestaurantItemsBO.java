package com.restaurants.bo;

import java.io.Serializable;
import java.util.List;

public class RestaurantItemsBO implements Serializable{

	private String orderId;
	private String itemId;
	private String restaurantId;
	private String totalCost;
	private List<OrderDetailsBO> orderDetailsBOS;

	public RestaurantItemsBO() {
	}


	public List<OrderDetailsBO> getOrderDetailsBOS() {
		return orderDetailsBOS;
	}

	public void setOrderDetailsBOS(List<OrderDetailsBO> orderDetailsBOS) {
		this.orderDetailsBOS = orderDetailsBOS;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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
}
