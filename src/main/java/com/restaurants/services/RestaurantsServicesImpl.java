package com.restaurants.services;

import com.restaurants.db.mariadb.dao.RestaurantsDao;
import com.restaurants.db.mariadb.entity.RestaurantsEntity;
import com.restaurants.services.interfaces.RestaurantsServices;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.RestaurantsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantsServicesImpl implements RestaurantsServices {
    private final RestaurantsDao restaurantsDao;
    private final SystemHelper helper;

    @Autowired
    public RestaurantsServicesImpl(RestaurantsDao restaurantsDao, SystemHelper helper) {
        this.restaurantsDao = restaurantsDao;
        this.helper = helper;
    }

    @Override
    public boolean doesExistId(Object id) {
        return getById(id) != null;
    }

    @Override
    public Object getById(Object id) {
        RestaurantsEntity entity = restaurantsDao.getById((Long) id);
        return getVO(entity);
    }

    @Override
    public Object save(Object obj) {
        if (obj == null) return null;
        RestaurantsEntity entity = (RestaurantsEntity) getEntity(obj);
        entity = restaurantsDao.save(entity);
        return getVO(entity);
    }

    @Override
    public Object getVO(Object obj) {
        if (obj == null) return null;
        RestaurantsEntity entity = (RestaurantsEntity) obj;
        RestaurantsVO vo = new RestaurantsVO();
        vo.setRestaurantId(entity.getRestaurantId());
        vo.setEmail(entity.getEmail());
        vo.setPhone(entity.getPhone());
        vo.setName(entity.getName());
        vo.setAddress(entity.getAddress());
        vo.setPasswordEncrypted(entity.getPasswordEncrypted());
        vo.setActiveOrNot(entity.getActiveOrNot());
        return vo;
    }

    @Override
    public Object getEntity(Object obj) {
        RestaurantsVO vo = (RestaurantsVO) obj;
        RestaurantsEntity entity = (RestaurantsEntity) getById(vo.getRestaurantId());
        if (helper.isEmpty(entity)) {
            entity = new RestaurantsEntity();
            entity.setCreatedTimestamp(vo.getCreatedTimestamp());
            entity.setCreatedBy(vo.getCreatedBy());
        }
        entity.setEmail(vo.getEmail());
        entity.setPhone(vo.getPhone());
        entity.setName(vo.getName());
        entity.setAddress(vo.getAddress());
        entity.setPasswordEncrypted(vo.getPasswordEncrypted());
        entity.setActiveOrNot(vo.getActiveOrNot());
        return entity;
    }

    @Override
    public RestaurantsVO getByPhoneOrMail(String phoneOrMail) {
        RestaurantsEntity entity = helper.isPhoneNumberFormat(phoneOrMail) ? restaurantsDao.getByMobile(phoneOrMail) : restaurantsDao.getByEmail(phoneOrMail);
        return (RestaurantsVO) getVO(entity);
    }
}
