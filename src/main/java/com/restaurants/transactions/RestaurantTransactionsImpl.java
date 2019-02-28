package com.restaurants.transactions;

import com.restaurants.services.interfaces.RestaurantsServices;
import com.restaurants.transactions.interfaces.RestaurantsTransactions;
import com.restaurants.vo.RestaurantsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
