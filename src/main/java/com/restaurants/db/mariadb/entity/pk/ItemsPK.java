package com.restaurants.db.mariadb.entity.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ItemsPK implements Serializable {

	private static final long serialVersionUID = 7181436844744152053L;

	private String itemName;
	private String itemType;

	public ItemsPK() {
	}

	public ItemsPK(String itemName, String itemType) {
		this.itemName = itemName;
		this.itemType = itemType;
	}

	@Column(name = "item_name", nullable = false, length = 28)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "item_type", nullable = false, length = 28)
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ItemsPK))
			return false;
		ItemsPK castOther = (ItemsPK) other;

		return (this.itemName == castOther.getItemName())
				&& ((this.getItemType() == castOther.getItemType()) || (this.getItemType() != null
						&& castOther.getItemType() != null && this.getItemType().equals(castOther.getItemType())));
	}

	public int hashCode() {
		Integer result = 17;

		result = 37 * result + (getItemName() == null ? 0 : this.getItemName().hashCode());
		result = 37 * result + (getItemType() == null ? 0 : this.getItemType().hashCode());
		return result;
	}
}
