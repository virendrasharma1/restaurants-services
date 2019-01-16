package com.restaurants.restaurants.services;

import com.restaurants.restaurants.db.mariadb.dao.RestaurantsDao;
import com.restaurants.restaurants.db.mariadb.entity.RestaurantsEntity;
import com.restaurants.restaurants.services.interfaces.RestaurantsServices;
import com.restaurants.restaurants.utils.SystemHelper;
import com.restaurants.restaurants.vo.RestaurantsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantsServicesImpl implements RestaurantsServices {
	private final RestaurantsDao restaurantsDao;
	private final SystemHelper helper;

	@Autowired
	public RestaurantsServicesImpl(RestaurantsDao restaurantsDao, SystemHelper helper) {
		this.restaurantsDao = restaurantsDao;
		this.helper = helper;
	}

	@Override
	public boolean doesExistId(Object id) {
		return  getById(id) != null;
	}

	@Override
	public Object getById(Object id) {
		RestaurantsEntity entity = restaurantsDao.getById((Long) id);
		return getVO(entity);
	}

	@Override
	public Object save(Object obj) {
		if (obj == null) return null;
		RestaurantsEntity entity = (RestaurantsEntity) getEntity(obj);
		entity = restaurantsDao.save(entity);
		return getVO(entity);
	}

	@Override
	public Object getVO(Object obj) {
		if (obj == null) return null;
		RestaurantsEntity entity = (RestaurantsEntity) obj;
		RestaurantsVO vo = new RestaurantsVO();
		vo.setRestaurantId(entity.getRestaurantId());
		vo.setEmail(entity.getEmail());
		vo.setPhone(entity.getPhone());
		vo.setName(entity.getName());
		vo.setAddress(entity.getAddress());
		vo.setCityId(entity.getCityId());
		vo.setStateId(entity.getStateId());
		vo.setPasswordEncrypted(entity.getPasswordEncrypted());
		vo.setActiveOrNot(entity.getActiveOrNot());
		vo.setType(entity.getType());
		return vo;
	}

	@Override
	public Object getEntity(Object obj) {
		RestaurantsVO vo = (RestaurantsVO) obj;
		RestaurantsEntity entity = (RestaurantsEntity) getById(vo.getRestaurantId());
		entity.setRestaurantId(vo.getRestaurantId());
		entity.setEmail(vo.getEmail());
		entity.setPhone(vo.getPhone());
		entity.setName(vo.getName());
		entity.setAddress(vo.getAddress());
		entity.setCityId(vo.getCityId());
		entity.setStateId(vo.getStateId());
		entity.setPasswordEncrypted(vo.getPasswordEncrypted());
		entity.setActiveOrNot(vo.getActiveOrNot());
		entity.setType(vo.getType());
		return entity;
	}

	@Override
	public RestaurantsVO getByPhoneOrMail(String phoneOrMail) {
		RestaurantsEntity entity = helper.isPhoneNumberFormat(phoneOrMail) ? restaurantsDao.getByMobile(phoneOrMail) : restaurantsDao.getByEmail(phoneOrMail);
		return (RestaurantsVO) getVO(entity);
	}
}
