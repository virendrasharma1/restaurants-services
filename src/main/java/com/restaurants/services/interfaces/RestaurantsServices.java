package com.restaurants.services.interfaces;

import com.restaurants.vo.RestaurantsVO;

public interface RestaurantsServices extends BaseServices {
	RestaurantsVO getByPhoneOrMail(String phoneOrMail);
}
