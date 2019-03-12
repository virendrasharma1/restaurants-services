package com.restaurants.services;

import com.restaurants.db.mariadb.dao.ItemsDao;
import com.restaurants.db.mariadb.dao.RestaurantItemsDao;
import com.restaurants.db.mariadb.dao.RestaurantsDao;
import com.restaurants.db.mariadb.entity.RestaurantItemsEntity;
import com.restaurants.services.interfaces.RestaurantItemsServices;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.GlobalItemsVO;
import com.restaurants.vo.RestaurantItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantItemsServicesImpl implements RestaurantItemsServices {
	private final RestaurantItemsDao restaurantItemsDao;
	private final RestaurantsDao restaurantsDao;
	private final ItemsDao itemsDao;
	private final SystemHelper helper;

	@Autowired
	public RestaurantItemsServicesImpl(RestaurantItemsDao restaurantItemsDao, RestaurantsDao restaurantsDao, ItemsDao itemsDao, SystemHelper helper) {
		this.restaurantItemsDao = restaurantItemsDao;
		this.restaurantsDao = restaurantsDao;
		this.itemsDao = itemsDao;
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
		vo.setItemName(entity.getItemsEntity().getId().getItemName());
		vo.setItemType(entity.getItemsEntity().getId().getItemType());
		vo.setRestaurantId(entity.getRestaurants().getRestaurantId());
		return vo;
	}

	@Override
	public Object getEntity(Object obj) {
		RestaurantItemsVO vo = (RestaurantItemsVO) obj;
		RestaurantItemsEntity entity = (RestaurantItemsEntity) getById(vo.getItemId());
		if (helper.isEmpty(entity)) {
			entity = new RestaurantItemsEntity();
			entity.setCreatedTimestamp(helper.getCurrentTime());
			entity.setCreatedBy(vo.getRestaurantId());
		}
		entity.setItemCost(vo.getItemCost());
		entity.setItemsEntity(itemsDao.getById(vo.getItemName(), vo.getItemType()));
		entity.setRestaurants(restaurantsDao.getById(vo.getRestaurantId()));
		entity.setItemId(vo.getItemId());
		return entity;
	}

	@Override
	public List<RestaurantItemsVO> getAllItemTypesOfRestaurant(Long restaurantId) {
		List<RestaurantItemsVO> vos = new ArrayList<>();
		List<RestaurantItemsEntity> entities = restaurantItemsDao.getAllItemTypesOfRestaurant(restaurantId);
		for (RestaurantItemsEntity entity: entities) {
			vos.add((RestaurantItemsVO) getVO(entity));
		}
		return vos;
	}

	@Override
	public List<RestaurantItemsVO> getItemNamesOfRestaurantOnTheBasisOfType(Long restaurantId, String type) {
		List<RestaurantItemsVO> vos = new ArrayList<>();
		List<RestaurantItemsEntity> entities = restaurantItemsDao.getItemNamesOfRestaurantOnTheBasisOfType(restaurantId, type);
		for (RestaurantItemsEntity entity: entities) {
			vos.add((RestaurantItemsVO) getVO(entity));
		}
		return vos;
	}
}
