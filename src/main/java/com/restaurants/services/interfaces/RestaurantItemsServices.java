package com.restaurants.services.interfaces;

import com.restaurants.vo.GlobalItemsVO;
import com.restaurants.vo.RestaurantItemsVO;

import java.util.List;

public interface RestaurantItemsServices extends BaseServices  {
    List<RestaurantItemsVO> getAllItemTypesOfRestaurant(Long restaurantId);

    List<RestaurantItemsVO> getItemNamesOfRestaurantOnTheBasisOfType(Long restaurantId, String type);
}
