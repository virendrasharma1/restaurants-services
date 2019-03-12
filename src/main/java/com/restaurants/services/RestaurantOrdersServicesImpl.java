package com.restaurants.services;

import com.restaurants.db.mariadb.dao.RestaurantOrdersDao;
import com.restaurants.db.mariadb.dao.RestaurantsDao;
import com.restaurants.db.mariadb.entity.RestaurantOrdersEntity;
import com.restaurants.services.interfaces.OrderDetailsServices;
import com.restaurants.services.interfaces.RestaurantOrdersServices;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.RestaurantOrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrdersServicesImpl implements RestaurantOrdersServices {
	private final RestaurantOrdersDao restaurantOrdersDao;
	private final RestaurantsDao restaurantsDao;
	private final OrderDetailsServices orderDetailsServices;
	private final SystemHelper helper;

	@Autowired
	public RestaurantOrdersServicesImpl(RestaurantOrdersDao restaurantOrdersDao, RestaurantsDao restaurantsDao, OrderDetailsServices orderDetailsServices, SystemHelper helper) {
		this.restaurantOrdersDao = restaurantOrdersDao;
		this.restaurantsDao = restaurantsDao;
		this.orderDetailsServices = orderDetailsServices;
		this.helper = helper;
	}

	@Override
	public boolean doesExistId(Object id) {
		return  getById(id) != null;
	}

	@Override
	public Object getById(Object id) {
		RestaurantOrdersEntity entity = restaurantOrdersDao.getById((Long) id);
		return getVO(entity);
	}

	@Override
	public Object save(Object obj) {
		if (obj == null) return null;
		RestaurantOrdersEntity entity = (RestaurantOrdersEntity) getEntity(obj);
		entity = restaurantOrdersDao.save(entity);
		return getVO(entity);
	}

	@Override
	public Object getVO(Object obj) {
		if (obj == null) return null;
		RestaurantOrdersEntity entity = (RestaurantOrdersEntity) obj;
		RestaurantOrdersVO vo = new RestaurantOrdersVO();
		vo.setOrderId(entity.getOrderId());
		vo.setRestaurantId(entity.getRestaurants().getRestaurantId());
		vo.setTotalCost(entity.getTotalCost());
		vo.setOrderedTime(entity.getOrderedTime());
		vo.setOrderDetailsVOS(orderDetailsServices.getRestaurantOrderDetailsByOrderId(entity.getOrderId()));
		return vo;
	}

	@Override
	public Object getEntity(Object obj) {
		RestaurantOrdersVO vo = (RestaurantOrdersVO) obj;
		RestaurantOrdersEntity entity = (RestaurantOrdersEntity) getById(vo.getOrderId());
		if (entity == null) {
			entity = new RestaurantOrdersEntity();
			entity.setCreatedBy(vo.getRestaurantId());
			entity.setCreatedTimestamp(helper.getCurrentTime());
		}
		entity.setOrderId(vo.getOrderId());
		entity.setRestaurants(restaurantsDao.getById(vo.getRestaurantId()));
		entity.setTotalCost(vo.getTotalCost());
		entity.setOrderedTime(vo.getOrderedTime());
		return entity;
	}
}
