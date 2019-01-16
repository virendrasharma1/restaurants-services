package com.restaurants.restaurants.converters;


import com.restaurants.restaurants.bo.RestaurantsBO;
import com.restaurants.restaurants.vo.RestaurantsVO;
import org.springframework.stereotype.Component;

@Component
public class RestaurantsConverter {
	public RestaurantsVO getRestaurantsVO(RestaurantsBO bo) {
		RestaurantsVO vo = new RestaurantsVO();
		vo.setRestaurantId(Long.valueOf(bo.getRestaurantId()));
		vo.setEmail(bo.getEmail());
		vo.setPhone(bo.getPhone());
		vo.setName(bo.getName());
		vo.setAddress(bo.getAddress());
		vo.setCityId(bo.getCityId());
		vo.setStateId(bo.getStateId());
		vo.setPasswordEncrypted(bo.getPasswordEncrypted());
		vo.setActiveOrNot(bo.getActiveOrNot());
		vo.setType(bo.getType());
		return vo;
	}

	public RestaurantsBO getRestaurantsBO(RestaurantsVO vo) {
		RestaurantsBO bo = new RestaurantsBO();
		bo.setRestaurantId(String.valueOf(vo.getRestaurantId()));
		bo.setEmail(vo.getEmail());
		bo.setPhone(vo.getPhone());
		bo.setName(vo.getName());
		bo.setAddress(vo.getAddress());
		bo.setCityId(vo.getCityId());
		bo.setStateId(vo.getStateId());
		bo.setPasswordEncrypted(vo.getPasswordEncrypted());
		bo.setActiveOrNot(vo.getActiveOrNot());
		bo.setType(vo.getType());
		return bo;
	}
}
