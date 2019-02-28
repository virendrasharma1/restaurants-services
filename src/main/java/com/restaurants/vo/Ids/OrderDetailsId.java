package com.restaurants.vo.Ids;

import java.io.Serializable;

public class OrderDetailsId implements Serializable{

	private Long orderId;
	private Long itemId;

	public OrderDetailsId(Long orderId, Long itemId) {
		this.orderId = orderId;
		this.itemId = itemId;
	}

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
}
