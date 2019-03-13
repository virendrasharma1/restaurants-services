package com.restaurants.bo;

import java.io.Serializable;

public class OrderDetailsBO implements Serializable{

	private String orderId;
	private String itemId;
	private String numberOfItems;
	private String totalCost;
	private String itemName;
	private String itemType;

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

	public String getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(String numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}
