package com.restaurants.transactions;

import com.restaurants.exceptions.AppErrorException;
import com.restaurants.exceptions.UserAuthenticationException;
import com.restaurants.services.interfaces.RestaurantsServices;
import com.restaurants.transactions.interfaces.RestaurantsTransactions;
import com.restaurants.utils.Constants;
import com.restaurants.utils.PasswordUtils;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.RestaurantsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTransactionsImpl implements RestaurantsTransactions {

    private final RestaurantsServices restaurantsServices;
    private final SystemHelper helper;

    @Autowired
    public RestaurantTransactionsImpl(RestaurantsServices restaurantsServices, SystemHelper helper) {
        this.restaurantsServices = restaurantsServices;
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
}
