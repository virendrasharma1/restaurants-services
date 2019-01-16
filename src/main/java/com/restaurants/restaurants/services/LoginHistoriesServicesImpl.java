package com.restaurants.restaurants.services;

import com.restaurants.restaurants.db.mariadb.dao.LoginHistoriesDao;
import com.restaurants.restaurants.db.mariadb.dao.RestaurantsDao;
import com.restaurants.restaurants.db.mariadb.entity.LoginHistoriesEntity;
import com.restaurants.restaurants.services.interfaces.LoginHistoriesServices;
import com.restaurants.restaurants.vo.LoginHistoriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginHistoriesServicesImpl implements LoginHistoriesServices {

    private final LoginHistoriesDao loginHistoriesDao;
    private final RestaurantsDao restaurantsDao;

    @Autowired
    public LoginHistoriesServicesImpl(LoginHistoriesDao loginHistoriesDao, RestaurantsDao restaurantsDao) {
        this.loginHistoriesDao = loginHistoriesDao;
	    this.restaurantsDao = restaurantsDao;
    }

    @Override
    public boolean doesExistId(Object id) {
        return getById((Long) id) != null;
    }

    @Override
    public Object getById(Object id) {
        LoginHistoriesEntity entity = loginHistoriesDao.getById((Long) id);
        return getVO(entity);
    }


    @Override
    public Object save(Object obj) {
        if (obj == null) return null;
        LoginHistoriesEntity entity = (LoginHistoriesEntity) getEntity(obj);
        entity = saveEntity(entity);
        return getVO(entity);
    }

    private LoginHistoriesEntity saveEntity(LoginHistoriesEntity entity) {
        return loginHistoriesDao.save(entity);
    }

    @Override
    public Object getVO(Object obj) {
        if (obj == null) return null;
        LoginHistoriesEntity entity = (LoginHistoriesEntity) obj;
        LoginHistoriesVO vo = new LoginHistoriesVO();
        vo.setLoginId(entity.getRestaurantsEntity().getRestaurantId());
        vo.setLoginDatetime(entity.getLoginDatetime());
        vo.setDeviceId(entity.getDeviceId());
        vo.setLatitudeLongitude(entity.getLatitudeLongitude());
        vo.setIpAddress(entity.getIpAddress());
        vo.setLoginStatus(entity.getLoginStatus());
        return vo;
    }

    @Override
    public Object getEntity(Object obj) {
        if (obj == null) return null;
        LoginHistoriesVO vo = (LoginHistoriesVO) obj;
        LoginHistoriesEntity entity = new LoginHistoriesEntity();
        entity.setRestaurantsEntity(restaurantsDao.getById(vo.getLoginId()));
        entity.setLoginDatetime(vo.getLoginDatetime());
        entity.setDeviceId(vo.getDeviceId());
        entity.setIpAddress(vo.getIpAddress());
        entity.setLoginStatus(vo.getLoginStatus());
        entity.setLatitudeLongitude(vo.getLatitudeLongitude());
        return entity;
    }
}
