package com.restaurants.services;

import com.restaurants.db.mariadb.dao.OrderDetailsDao;
import com.restaurants.db.mariadb.dao.RestaurantOrdersDao;
import com.restaurants.db.mariadb.dao.RestaurantsDao;
import com.restaurants.db.mariadb.entity.OrderDetailsEntity;
import com.restaurants.db.mariadb.entity.pk.OrderDetailsPK;
import com.restaurants.services.interfaces.OrderDetailsServices;
import com.restaurants.services.interfaces.RestaurantOrdersServices;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.Ids.OrderDetailsId;
import com.restaurants.vo.OrderDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDetailsServicesImpl implements OrderDetailsServices {
	private final OrderDetailsDao orderDetailsDao;
	private final RestaurantsDao restaurantsDao;
	private final SystemHelper helper;

	@Autowired
	public OrderDetailsServicesImpl(OrderDetailsDao orderDetailsDao, RestaurantsDao restaurantsDao, SystemHelper helper) {
		this.orderDetailsDao = orderDetailsDao;
		this.restaurantsDao = restaurantsDao;
		this.helper = helper;
	}

	@Override
	public boolean doesExistId(Object id) {
		return  getById(id) != null;
	}

	@Override
	public Object getById(Object id) {
		OrderDetailsId pk = (OrderDetailsId) id;
		OrderDetailsEntity entity = getById(pk.getOrderId(), pk.getItemId());
		return getVO(entity);
	}

	private OrderDetailsEntity getById(Long articlePinboardId, Long blockedBy) {
		return orderDetailsDao.getById(articlePinboardId, blockedBy);
	}

	@Override
	public Object save(Object obj) {
		if (obj == null) return null;
		OrderDetailsEntity entity = (OrderDetailsEntity) getEntity(obj);
		entity = orderDetailsDao.save(entity);
		return getVO(entity);
	}

	@Override
	public Object getVO(Object obj) {
		if (obj == null) return null;
		OrderDetailsEntity entity = (OrderDetailsEntity) obj;
		OrderDetailsVO vo = new OrderDetailsVO();
		OrderDetailsId id = new OrderDetailsId(entity.getId().getItemId(), entity.getId().getOrderId());
		vo.setId(id);
		vo.setNumberOfItems(entity.getNumberOfItems());
		vo.setTotalCost(entity.getTotalCost());
		return vo;
	}

	@Override
	public Object getEntity(Object obj) {
		OrderDetailsVO vo = (OrderDetailsVO) obj;
		OrderDetailsEntity entity = getById(vo.getId().getOrderId(), vo.getId().getItemId());
		if (entity == null) {
			entity = new OrderDetailsEntity();
			OrderDetailsPK pk = new OrderDetailsPK(vo.getId().getOrderId(), vo.getId().getItemId());
			entity.setId(pk);
		}
		entity.setNumberOfItems(vo.getNumberOfItems());
		entity.setTotalCost(vo.getTotalCost());
		return entity;
	}

	@Override
	public List<OrderDetailsVO> getRestaurantOrderDetailsByOrderId(Long orderId) {
		List<OrderDetailsVO> vos = new ArrayList<>();
		List<OrderDetailsEntity> entities = orderDetailsDao.getRestaurantOrderDetailsByOrderId(orderId);
		for (OrderDetailsEntity entity: entities ) {
			vos.add((OrderDetailsVO) getVO(entity));
		}
		return vos;
	}
}
