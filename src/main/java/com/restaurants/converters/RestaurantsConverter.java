package com.restaurants.converters;


import com.restaurants.bo.RestaurantsBO;
import com.restaurants.exceptions.DataFormatException;
import com.restaurants.utils.DataParser;
import com.restaurants.vo.RestaurantsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantsConverter {
	@Autowired
	protected DataParser dataParser;
	public RestaurantsVO getRestaurantsVO(RestaurantsBO bo) throws DataFormatException {
		RestaurantsVO vo = new RestaurantsVO();
		vo.setRestaurantId(dataParser.getNullableLongValue(bo.getRestaurantId()));
		vo.setEmail(bo.getEmail());
		vo.setPhone(bo.getPhone());
		vo.setName(bo.getName());
		vo.setAddress(bo.getAddress());
		vo.setPasswordEncrypted(bo.getPassword());
		vo.setActiveOrNot(bo.getActiveOrNot());
		vo.setEmailOrPhone(bo.getEmailOrPhone());
		return vo;
	}

	public RestaurantsBO getRestaurantsBO(RestaurantsVO vo) {
		RestaurantsBO bo = new RestaurantsBO();
		bo.setRestaurantId(String.valueOf(vo.getRestaurantId()));
		bo.setEmail(vo.getEmail());
		bo.setPhone(vo.getPhone());
		bo.setName(vo.getName());
		bo.setAddress(vo.getAddress());
		bo.setPasswordEncrypted(vo.getPasswordEncrypted());
		bo.setActiveOrNot(vo.getActiveOrNot());
		return bo;
	}
}
