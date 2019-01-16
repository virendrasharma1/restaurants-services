package com.restaurants.restaurants.transactions.interfaces;

import com.restaurants.restaurants.vo.RestaurantsVO;
import org.springframework.stereotype.Component;

@Component
public interface RestaurantsTransactions {
	RestaurantsVO register(RestaurantsVO vo);

	RestaurantsVO ifAlreadyRegistered(String phoneOrMail);
}
