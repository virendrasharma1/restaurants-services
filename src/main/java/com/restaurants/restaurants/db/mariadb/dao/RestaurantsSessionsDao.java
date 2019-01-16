package com.restaurants.restaurants.db.mariadb.dao;

import com.restaurants.restaurants.db.mariadb.entity.RestaurantSessionsEntity;
import com.restaurants.restaurants.db.mariadb.entity.pk.RestaurantsSessionsPK;
import com.restaurants.restaurants.utils.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RestaurantsSessionsDao extends JpaRepository<RestaurantSessionsEntity, RestaurantsSessionsPK> {

    @Query("SELECT u FROM RestaurantSessionsEntity u WHERE u.id.restaurantId = :restaurantId AND u.id.deviceId = :deviceId")
    RestaurantSessionsEntity getById(@Param("restaurantId") Long restaurantId, @Param("deviceId") String deviceId);

	@Query("SELECT u FROM RestaurantSessionsEntity u WHERE u.id.restaurantId = :restaurantId AND u.status= '" + Constants.STATUS_ACTIVE + "' ORDER BY u.loginDatetime DESC")
	List<RestaurantSessionsEntity> getActiveSessions(@Param("restaurantId") Long restaurantId);

	@Modifying
    @Query("UPDATE RestaurantSessionsEntity u SET u.gcmToken = :gcmToken WHERE u.id.restaurantId = :restaurantId AND u.id.deviceId = :deviceId")
    int updateGcmToken(@Param("restaurantId") Long restaurantId, @Param("deviceId") String deviceId, @Param("gcmToken") String gcmToken);

	@Modifying
	@Query("UPDATE RestaurantSessionsEntity u SET u.status = :status, u.logoutOrInvalidationDatetime = :localDateTime  WHERE u.id.restaurantId = :restaurantId " +
			"AND u.id.deviceId = :deviceId AND u.status= '" + Constants.STATUS_ACTIVE + "'")
	int updateStatus(@Param("restaurantId") Long restaurantId, @Param("deviceId") String deviceId, @Param("status") String status, @Param("localDateTime") LocalDateTime localDateTime);

	@Modifying
    @Query("UPDATE RestaurantSessionsEntity u SET u.status = '" + Constants.STATUS_STALE + "', u.logoutOrInvalidationDatetime = :localDateTime WHERE u.id.restaurantId= :restaurantId AND u.status= '" + Constants.STATUS_ACTIVE + "'")
    int invalidateActiveSessions(@Param("restaurantId") Long restaurantId, @Param("localDateTime") LocalDateTime localDateTime);

	@Query("SELECT u FROM RestaurantSessionsEntity u WHERE u.id.restaurantId = :restaurantId")
	List<RestaurantSessionsEntity> getAllAspirantSessionsThroughLoginId(@Param("restaurantId") Long restaurantId);
}
