package com.restaurants.restaurants.services.interfaces;

import com.restaurants.restaurants.vo.RestaurantsVO;

public interface RestaurantsServices extends BaseServices {
	RestaurantsVO getByPhoneOrMail(String phoneOrMail);
}
