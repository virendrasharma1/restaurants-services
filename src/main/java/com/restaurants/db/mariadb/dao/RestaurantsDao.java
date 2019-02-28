package com.restaurants.db.mariadb.dao;

import com.restaurants.db.mariadb.entity.RestaurantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsDao extends JpaRepository<RestaurantsEntity, Long> {

	@Query("SELECT a FROM RestaurantsEntity a WHERE a.restaurantId = :restaurantId")
	RestaurantsEntity getById(@Param("restaurantId") Long restaurantId);

	@Query("SELECT a FROM RestaurantsEntity a WHERE a.email = :email")
	RestaurantsEntity getByEmail(@Param("email") String email);

	@Query("SELECT a FROM RestaurantsEntity a WHERE a.phone = :phone")
	RestaurantsEntity getByMobile(@Param("phone") String phone);
}
