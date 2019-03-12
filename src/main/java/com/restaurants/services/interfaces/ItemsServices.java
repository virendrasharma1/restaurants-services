package com.restaurants.services.interfaces;

import com.restaurants.vo.GlobalItemsVO;

import java.util.List;

public interface ItemsServices extends BaseServices  {
    List<GlobalItemsVO> getAllItemTypes();

    List<GlobalItemsVO> getItemNamesWithGivenType(String type);
}
