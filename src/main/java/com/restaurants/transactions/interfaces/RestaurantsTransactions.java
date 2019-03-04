package com.restaurants.transactions.interfaces;

import com.restaurants.exceptions.AppErrorException;
import com.restaurants.exceptions.UserAuthenticationException;
import com.restaurants.vo.RestaurantsVO;
import org.springframework.stereotype.Component;

@Component
public interface RestaurantsTransactions {
	RestaurantsVO register(RestaurantsVO vo) throws AppErrorException;

	RestaurantsVO ifAlreadyRegistered(String phoneOrMail);

	Long login(String name, String value, String deviceId, String ipAddress) throws AppErrorException, UserAuthenticationException;
}
