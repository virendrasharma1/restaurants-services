package com.restaurants.vo.Ids;

import java.io.Serializable;

public class GlobalItemsId implements Serializable{

	private String itemName;
	private String itemType;


	public GlobalItemsId(String itemName, String itemType) {
		this.itemName = itemName;
		this.itemType = itemType;
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
