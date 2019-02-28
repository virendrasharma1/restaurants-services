package com.restaurants.vo;

import com.restaurants.vo.Ids.OrderDetailsId;

import java.io.Serializable;

public class OrderDetailsVO implements Serializable{

	private OrderDetailsId id;
	private Integer numberOfItems;
	private Double totalCost;

	public OrderDetailsId getId() {
		return id;
	}

	public void setId(OrderDetailsId id) {
		this.id = id;
	}

	public Integer getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
}
