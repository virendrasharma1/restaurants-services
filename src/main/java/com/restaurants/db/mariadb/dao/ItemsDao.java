package com.restaurants.db.mariadb.dao;

import com.restaurants.db.mariadb.entity.ItemsEntity;
import com.restaurants.db.mariadb.entity.pk.ItemsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsDao extends JpaRepository<ItemsEntity, ItemsPK> {

	@Query("SELECT a FROM ItemsEntity a WHERE a.id.itemName = :itemName AND a.id.itemType = :itemType")
	ItemsEntity getById(@Param("itemName") String itemName, @Param("itemType") String itemType);

	@Query("SELECT a FROM ItemsEntity a WHERE a.id.itemType LIKE :itemType GROUP BY a.id.itemType")
	List<ItemsEntity> getItemTypes(@Param("itemType") String itemType);

	@Query("SELECT a FROM ItemsEntity a GROUP BY a.id.itemType")
	List<ItemsEntity> getAllItemTypes();

	@Query("SELECT DISTINCT a FROM ItemsEntity a WHERE a.id.itemType = :itemType AND a.id.itemName LIKE :itemName")
	List<ItemsEntity> getItemNamesWithGivenType(@Param("itemType") String itemType, @Param("itemName") String itemName);
}
