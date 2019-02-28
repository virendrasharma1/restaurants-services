package com.restaurants.db.mariadb.dao;

import com.restaurants.db.mariadb.entity.OrderDetailsEntity;
import com.restaurants.db.mariadb.entity.pk.OrderDetailsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsDao extends JpaRepository<OrderDetailsEntity, OrderDetailsPK> {

	@Query("SELECT a FROM OrderDetailsEntity a WHERE a.id.orderId = :orderId AND a.id.itemId = :itemId")
	OrderDetailsEntity getById(@Param("orderId") Long orderId, @Param("itemId") Long itemId);

	@Query("SELECT a FROM OrderDetailsEntity a WHERE a.id.orderId = :orderId")
	List<OrderDetailsEntity> getRestaurantOrderDetailsByOrderId(@Param("orderId") Long orderId);
}
