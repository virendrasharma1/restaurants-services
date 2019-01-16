package com.restaurants.restaurants.db.mariadb.dao;

import com.restaurants.restaurants.db.mariadb.entity.RestaurantsEntity;
import com.restaurants.restaurants.vo.RestaurantsVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsDao extends JpaRepository<RestaurantsEntity, Long> {

	@Query("SELECT a FROM RestaurantsEntity a WHERE a.restaurantId = :restaurantId")
	RestaurantsEntity getById(@Param("restaurantId") Long restaurantId);

	@Query("SELECT a FROM RestaurantsEntity a WHERE a.email = :email")
	RestaurantsEntity getByEmail(@Param("restaurantId") String email);

	@Query("SELECT a FROM RestaurantsEntity a WHERE a.phone = :phone")
	RestaurantsEntity getByMobile(@Param("restaurantId") String phone);
}
