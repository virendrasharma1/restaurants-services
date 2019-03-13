package com.restaurants.services.interfaces;

import com.restaurants.vo.RestaurantOrdersVO;

import java.util.List;

public interface RestaurantOrdersServices extends BaseServices  {
    List<RestaurantOrdersVO> getRestaurantOrders(Long restaurantId);
}
