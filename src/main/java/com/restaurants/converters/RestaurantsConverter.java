package com.restaurants.converters;


import com.restaurants.bo.*;
import com.restaurants.exceptions.DataFormatException;
import com.restaurants.utils.DataParser;
import com.restaurants.vo.*;
import com.restaurants.vo.Ids.OrderDetailsId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantsConverter {
	@Autowired
	protected DataParser dataParser;
	public RestaurantsVO getRestaurantsVO(RestaurantsBO bo) throws DataFormatException {
		RestaurantsVO vo = new RestaurantsVO();
		vo.setRestaurantId(dataParser.getNullableLongValue(bo.getRestaurantId()));
		vo.setEmail(bo.getEmail());
		vo.setPhone(bo.getPhone());
		vo.setName(bo.getName());
		vo.setAddress(bo.getAddress());
		vo.setPasswordEncrypted(bo.getPassword());
		vo.setActiveOrNot(bo.getActiveOrNot());
		vo.setEmailOrPhone(bo.getEmailOrPhone());
		return vo;
	}

	public RestaurantsBO getRestaurantsBO(RestaurantsVO vo) {
		RestaurantsBO bo = new RestaurantsBO();
		bo.setRestaurantId(dataParser.getLongStringValue(vo.getRestaurantId()));
		bo.setEmail(vo.getEmail());
		bo.setPhone(vo.getPhone());
		bo.setName(vo.getName());
		bo.setAddress(vo.getAddress());
		bo.setPasswordEncrypted(vo.getPasswordEncrypted());
		bo.setActiveOrNot(vo.getActiveOrNot());
		return bo;
	}

    public RestaurantItemsVO getRestaurantItem(RestaurantItemsBO bo) throws DataFormatException {
		RestaurantItemsVO vo = new RestaurantItemsVO();
		vo.setItemName(bo.getItemName());
		vo.setItemId(dataParser.getNullableLongValue(bo.getItemId()));
		vo.setItemCost(dataParser.getDoubleValue(bo.getItemCost()));
		vo.setRestaurantId(dataParser.getLongValue(bo.getRestaurantId()));
		vo.setItemType(bo.getItemType());
		return vo;
    }

    public List<GlobalItmesBO> getGlobalItemsListBO(List<GlobalItemsVO> vos) {
		List<GlobalItmesBO> list = new ArrayList<>();
		for (GlobalItemsVO vo : vos) {
			list.add(getGlobalItemsBO(vo));
		}
		return list;
    }

	private GlobalItmesBO getGlobalItemsBO(GlobalItemsVO vo) {
		GlobalItmesBO bo = new GlobalItmesBO();
		bo.setItemName(vo.getId().getItemName());
		bo.setItemType(vo.getId().getItemType());
		return bo;
	}

    public List<RestaurantItemsBO> getRestaurantItemListBO(List<RestaurantItemsVO> vos) {
		List<RestaurantItemsBO> list = new ArrayList<>();
		for (RestaurantItemsVO vo : vos) {
			list.add(getRestaurantItemsBO(vo));
		}
		return list;
    }

	private RestaurantItemsBO getRestaurantItemsBO(RestaurantItemsVO vo) {
		RestaurantItemsBO bo = new RestaurantItemsBO();
		bo.setItemId(dataParser.getLongStringValue(vo.getItemId()));
		bo.setItemName(vo.getItemName());
		bo.setItemType(vo.getItemType());
		bo.setRestaurantId(dataParser.getLongStringValue(vo.getRestaurantId()));
		bo.setItemCost(dataParser.getDouble2StringValue(vo.getItemCost()));
		return bo;
	}

    public RestaurantOrdersVO getRestaurantOrderVO(RestaurantOrdersBO bo) throws DataFormatException {
		RestaurantOrdersVO vo = new RestaurantOrdersVO();
		vo.setRestaurantId(dataParser.getLongValue(bo.getRestaurantId()));
		vo.setOrderDetailsVOS(getOrderDetailsVOList(bo.getOrderDetailsBOS()));
		vo.setTotalCost(dataParser.getDoubleValue(bo.getTotalCost()));
		return vo;
    }

	private List<OrderDetailsVO> getOrderDetailsVOList(List<OrderDetailsBO> orderDetailsBOS) throws DataFormatException {
		List<OrderDetailsVO> list = new ArrayList<>();
		for (OrderDetailsBO bo : orderDetailsBOS) {
			list.add(getOrderDetailsVO(bo));
		}
		return list;
	}

	private OrderDetailsVO getOrderDetailsVO(OrderDetailsBO bo) throws DataFormatException {
		OrderDetailsVO vo = new OrderDetailsVO();
		vo.setNumberOfItems(dataParser.getIntegerValue(bo.getNumberOfItems()));
		vo.setTotalCost(dataParser.getDoubleValue(bo.getTotalCost()));
		OrderDetailsId id = new OrderDetailsId(dataParser.getLongValue(bo.getOrderId()), dataParser.getLongValue(bo.getItemId()));
		vo.setId(id);
		return vo;
	}
}
