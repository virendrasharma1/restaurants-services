package com.restaurants.services;

import com.restaurants.db.mariadb.dao.RestaurantItemsDao;
import com.restaurants.db.mariadb.dao.RestaurantsDao;
import com.restaurants.db.mariadb.entity.RestaurantItemsEntity;
import com.restaurants.services.interfaces.RestaurantItemsServices;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.Ids.OrderDetailsId;
import com.restaurants.vo.OrderDetailsVO;
import com.restaurants.vo.RestaurantItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantItemsServicesImpl implements RestaurantItemsServices {
	private final RestaurantItemsDao restaurantItemsDao;
	private final RestaurantsDao restaurantsDao;
	private final SystemHelper helper;

	@Autowired
	public RestaurantItemsServicesImpl(RestaurantItemsDao restaurantItemsDao, RestaurantsDao restaurantsDao, SystemHelper helper) {
		this.restaurantItemsDao = restaurantItemsDao;
		this.restaurantsDao = restaurantsDao;
		this.helper = helper;
	}

	@Override
	public boolean doesExistId(Object id) {
		return  getById(id) != null;
	}

	@Override
	public Object getById(Object id) {
		RestaurantItemsEntity entity = restaurantItemsDao.getById((Long) id);
		return getVO(entity);
	}

	@Override
	public Object save(Object obj) {
		if (obj == null) return null;
		RestaurantItemsEntity entity = (RestaurantItemsEntity) getEntity(obj);
		entity = restaurantItemsDao.save(entity);
		return getVO(entity);
	}

	@Override
	public Object getVO(Object obj) {
		if (obj == null) return null;
		RestaurantItemsEntity entity = (RestaurantItemsEntity) obj;
		RestaurantItemsVO vo = new RestaurantItemsVO();
		vo.setItemCost(entity.getItemCost());
		vo.setItemId(entity.getItemId());
		vo.setItemName(entity.getItemName());
		vo.setRestaurantId(entity.getRestaurants().getRestaurantId());
		return vo;
	}

	@Override
	public Object getEntity(Object obj) {
		RestaurantItemsVO vo = (RestaurantItemsVO) obj;
		RestaurantItemsEntity entity = (RestaurantItemsEntity) getById(vo.getItemId());
		entity.setItemCost(vo.getItemCost());
		entity.setItemName(vo.getItemName());
		entity.setRestaurants(restaurantsDao.getById(vo.getRestaurantId()));
		entity.setItemId(vo.getItemId());
		return entity;
	}
}
