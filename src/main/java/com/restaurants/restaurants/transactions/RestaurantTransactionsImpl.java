package com.restaurants.restaurants.transactions;

import com.restaurants.restaurants.services.interfaces.RestaurantsServices;
import com.restaurants.restaurants.transactions.interfaces.RestaurantsTransactions;
import com.restaurants.restaurants.vo.RestaurantsVO;
import org.springframework.beans.factory.annotation.Autowired;

public class RestaurantTransactionsImpl implements RestaurantsTransactions {

	private final RestaurantsServices restaurantsServices;
	@Autowired
	public RestaurantTransactionsImpl(RestaurantsServices restaurantsServices) {
		this.restaurantsServices = restaurantsServices;
	}

	@Override
	public RestaurantsVO register(RestaurantsVO vo) {
		vo = (RestaurantsVO) restaurantsServices.save(vo);
		return vo;
	}

	@Override
	public RestaurantsVO ifAlreadyRegistered(String phoneOrMail) {
		return restaurantsServices.getByPhoneOrMail(phoneOrMail);
	}
}
