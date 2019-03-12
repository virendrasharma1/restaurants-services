package com.restaurants.vo;

import com.restaurants.vo.Ids.GlobalItemsId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GlobalItemsVO implements Serializable{

	private GlobalItemsId id;
	private LocalDateTime createdTimestamp;

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public GlobalItemsId getId() {
		return id;
	}

	public void setId(GlobalItemsId id) {
		this.id = id;
	}
}
