package com.restaurants.db.mariadb.entity.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class OrderDetailsPK implements java.io.Serializable {

	private Long orderId;
	private Long itemId;

	public OrderDetailsPK() {
	}

	public OrderDetailsPK(Long orderId, Long itemId) {
		this.orderId = orderId;
		this.itemId = itemId;
	}

	@Column(name = "order_id", nullable = false)
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "item_id", nullable = false)
	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OrderDetailsPK))
			return false;
		OrderDetailsPK castOther = (OrderDetailsPK) other;

		return (this.getOrderId() == castOther.getOrderId()) && (this.getItemId() == castOther.getItemId());
	}

	public int hashCode() {
		int result = 17;

		result = (int) (37 * result + this.getOrderId());
		result = (int) (37 * result +  this.getItemId());
		return result;
	}

}
