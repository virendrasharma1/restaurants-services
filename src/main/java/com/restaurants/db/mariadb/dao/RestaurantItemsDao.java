package com.restaurants.db.mariadb.dao;

import com.restaurants.db.mariadb.entity.RestaurantItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantItemsDao extends JpaRepository<RestaurantItemsEntity, Long> {

    @Query("SELECT u FROM RestaurantItemsEntity u WHERE u.itemId = :itemId")
    RestaurantItemsEntity getById(@Param("itemId") Long itemId);

    @Query("SELECT u FROM RestaurantItemsEntity u WHERE u.restaurants.restaurantId = :restaurantId GROUP BY u.itemsEntity.id.itemType")
    List<RestaurantItemsEntity> getAllItemTypesOfRestaurant(@Param("restaurantId") Long restaurantId);

    @Query("SELECT u FROM RestaurantItemsEntity u WHERE u.restaurants.restaurantId = :restaurantId AND u.itemsEntity.id.itemType = :itemType")
    List<RestaurantItemsEntity> getItemNamesOfRestaurantOnTheBasisOfType(@Param("restaurantId") Long restaurantId, @Param("itemType") String itemType);
}
