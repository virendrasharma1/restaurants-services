package com.restaurants.transactions;

import com.restaurants.exceptions.AppErrorException;
import com.restaurants.exceptions.UserAuthenticationException;
import com.restaurants.services.interfaces.*;
import com.restaurants.transactions.interfaces.RestaurantsTransactions;
import com.restaurants.utils.Constants;
import com.restaurants.utils.PasswordUtils;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.*;
import com.restaurants.vo.Ids.GlobalItemsId;
import com.restaurants.vo.Ids.OrderDetailsId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantTransactionsImpl implements RestaurantsTransactions {

    private final RestaurantsServices restaurantsServices;
    private final RestaurantOrdersServices restaurantOrdersServices;
    private final RestaurantsSessionsServices restaurantsSessionsServices;
    private final RestaurantItemsServices restaurantItemsServices;
    private final OrderDetailsServices orderDetailsServices;
    private final ItemsServices itemsServices;
    private final SystemHelper helper;

    @Autowired
    public RestaurantTransactionsImpl(RestaurantsServices restaurantsServices, RestaurantOrdersServices restaurantOrdersServices, RestaurantsSessionsServices restaurantsSessionsServices, RestaurantItemsServices restaurantItemsServices, OrderDetailsServices orderDetailsServices, ItemsServices itemsServices, SystemHelper helper) {
        this.restaurantsServices = restaurantsServices;
        this.restaurantOrdersServices = restaurantOrdersServices;
        this.restaurantsSessionsServices = restaurantsSessionsServices;
        this.restaurantItemsServices = restaurantItemsServices;
        this.orderDetailsServices = orderDetailsServices;
        this.itemsServices = itemsServices;
        this.helper = helper;
    }

    @Override
    public RestaurantsVO register(RestaurantsVO vo) throws AppErrorException {
        if (helper.isPhoneNumberFormat(vo.getEmailOrPhone())) {
            vo.setPhone(vo.getEmailOrPhone());
        } else {
            vo.setEmail(vo.getEmailOrPhone());
        }
        vo.setPasswordEncrypted(new PasswordUtils().generateHash(vo.getPasswordEncrypted()));
        vo.setActiveOrNot(Constants.STATUS_ACTIVE);
        vo.setCreatedTimestamp(helper.getCurrentTime());
        vo.setCreatedBy(Constants.ROOT_USER);
        vo = (RestaurantsVO) restaurantsServices.save(vo);
        return vo;
    }

    @Override
    public RestaurantsVO ifAlreadyRegistered(String phoneOrMail) {
        return restaurantsServices.getByPhoneOrMail(phoneOrMail);
    }

    @Override
    public Long login(String name, String value, String deviceId, String ipAddress) throws AppErrorException, UserAuthenticationException {
        RestaurantsVO vo = restaurantsServices.getByPhoneOrMail(name);

        if (vo == null) {
            throw new UserAuthenticationException(name, Constants.NOT_REGISTERED);
        }

        if (!new PasswordUtils().compare(value, vo.getPasswordEncrypted())) {
            throw new UserAuthenticationException(name, Constants.CREDENTIALS_DO_NOT_MATCH);
        }
        return vo.getRestaurantId();
    }

    @Override
    public RestaurantItemsVO saveRestaurantItem(RestaurantItemsVO vo) {
        GlobalItemsVO globalItemsVO = new GlobalItemsVO();
        GlobalItemsId id = new GlobalItemsId(vo.getItemName(), vo.getItemType());
        globalItemsVO.setId(id);
        itemsServices.save(globalItemsVO);
        return (RestaurantItemsVO) restaurantItemsServices.save(vo);
    }

    @Override
    public List<GlobalItemsVO> getAllItemTypes(String search) {
        return itemsServices.getAllItemTypes(search);
    }

    @Override
    public List<GlobalItemsVO> getItemNamesWithGivenType(String type, String itemName) {
        return itemsServices.getItemNamesWithGivenType(type, itemName);
    }

    @Override
    public List<RestaurantItemsVO> getAllItemTypesOfRestaurant(Long restaurantId) {
        return restaurantItemsServices.getAllItemTypesOfRestaurant(restaurantId);
    }

    @Override
    public List<RestaurantItemsVO> getItemNamesOfRestaurantOnTheBasisOfType(Long restaurantId, String type) {
        return restaurantItemsServices.getItemNamesOfRestaurantOnTheBasisOfType(restaurantId, type);
    }

    @Override
    public RestaurantOrdersVO saveRestaurantOrder(RestaurantOrdersVO vo) {
        RestaurantOrdersVO restaurantOrdersVO = new RestaurantOrdersVO();
        restaurantOrdersVO.setTotalCost(vo.getTotalCost());
        restaurantOrdersVO.setOrderedTime(helper.getCurrentTime());
        restaurantOrdersVO.setRestaurantId(vo.getRestaurantId());
        restaurantOrdersVO = (RestaurantOrdersVO) restaurantOrdersServices.save(restaurantOrdersVO);
        for (OrderDetailsVO orderDetailsVO: vo.getOrderDetailsVOS()) {
            OrderDetailsId id = new OrderDetailsId(restaurantOrdersVO.getOrderId(), orderDetailsVO.getId().getItemId());
            orderDetailsVO.setId(id);
            orderDetailsVO.setNumberOfItems(orderDetailsVO.getNumberOfItems());
            orderDetailsVO.setTotalCost(orderDetailsVO.getTotalCost());
            orderDetailsServices.save(orderDetailsVO);
        }
        return restaurantOrdersVO;
    }

    @Override
    public List<RestaurantOrdersVO> getRestaurantOrders(Long restaurantId) {
        return restaurantOrdersServices.getRestaurantOrders(restaurantId);
    }

    @Override
    public void logout(Long loginId, String deviceId) {
        restaurantsSessionsServices.logout(loginId, deviceId);
    }

    @Override
    public RestaurantsVO getRestaurantProfile(Long restaurantId) {
        return (RestaurantsVO) restaurantsServices.getById(restaurantId);
    }
}
