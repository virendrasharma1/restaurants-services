package com.restaurants.db.mariadb.dao;

import com.restaurants.db.mariadb.entity.RestaurantItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantItemsDao extends JpaRepository<RestaurantItemsEntity, Long> {

    @Query("SELECT u FROM RestaurantItemsEntity u WHERE u.itemId = :itemId")
    RestaurantItemsEntity getById(@Param("itemId") Long itemId);
}
