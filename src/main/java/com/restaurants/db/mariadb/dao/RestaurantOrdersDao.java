package com.restaurants.db.mariadb.dao;

import com.restaurants.db.mariadb.entity.RestaurantOrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantOrdersDao extends JpaRepository<RestaurantOrdersEntity, Long> {

    @Query("SELECT u FROM RestaurantOrdersEntity u WHERE u.orderId = :orderId")
	RestaurantOrdersEntity getById(@Param("orderId") Long orderId);

    @Query("SELECT u FROM RestaurantOrdersEntity u WHERE u.restaurants.restaurantId = :restaurantId")
    List<RestaurantOrdersEntity> getRestaurantOrders(@Param("restaurantId") Long restaurantId);
}
