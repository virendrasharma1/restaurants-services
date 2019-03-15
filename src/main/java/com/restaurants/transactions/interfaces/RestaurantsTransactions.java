package com.restaurants.transactions.interfaces;

import com.restaurants.exceptions.AppErrorException;
import com.restaurants.exceptions.UserAuthenticationException;
import com.restaurants.vo.GlobalItemsVO;
import com.restaurants.vo.RestaurantItemsVO;
import com.restaurants.vo.RestaurantOrdersVO;
import com.restaurants.vo.RestaurantsVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RestaurantsTransactions {
	RestaurantsVO register(RestaurantsVO vo) throws AppErrorException;

	RestaurantsVO ifAlreadyRegistered(String phoneOrMail);

	Long login(String name, String value, String deviceId, String ipAddress) throws AppErrorException, UserAuthenticationException;

    RestaurantItemsVO saveRestaurantItem(RestaurantItemsVO vo);

    List<GlobalItemsVO> getAllItemTypes(String search);

	List<GlobalItemsVO> getItemNamesWithGivenType(String type, String itemName);

    List<RestaurantItemsVO> getAllItemTypesOfRestaurant(Long restaurantId);

    List<RestaurantItemsVO> getItemNamesOfRestaurantOnTheBasisOfType(Long restaurantId, String type);

    RestaurantOrdersVO saveRestaurantOrder(RestaurantOrdersVO vo);

    List<RestaurantOrdersVO> getRestaurantOrders(Long restaurantId);

    void logout(Long loginId, String deviceId);

    RestaurantsVO getRestaurantProfile(Long restaurantId);
}
