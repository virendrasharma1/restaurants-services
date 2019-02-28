package com.restaurants.services.interfaces;

import com.restaurants.vo.OrderDetailsVO;

import java.util.List;

public interface OrderDetailsServices extends BaseServices  {
    List<OrderDetailsVO> getRestaurantOrderDetailsByOrderId(Long orderId);
}
