package com.restaurants.services;

import com.restaurants.db.mariadb.dao.RestaurantsSessionsDao;
import com.restaurants.db.mariadb.entity.RestaurantSessionsEntity;
import com.restaurants.db.mariadb.entity.pk.RestaurantsSessionsPK;
import com.restaurants.services.interfaces.RestaurantsSessionsServices;
import com.restaurants.utils.Constants;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.Ids.RestaurantsSessionsId;
import com.restaurants.vo.RestaurantsSessionsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantsSessionsServicesImpl implements RestaurantsSessionsServices {

    private final RestaurantsSessionsDao restaurantsSessionsDao;
	@Autowired protected SystemHelper helper;

    @Autowired
    public RestaurantsSessionsServicesImpl(RestaurantsSessionsDao restaurantsSessionsDao) {
        this.restaurantsSessionsDao = restaurantsSessionsDao;
    }

    @Override
    public boolean doesExistId(Object id) {
        RestaurantsSessionsId pk = (RestaurantsSessionsId) id;
        return  getById(pk.getLoginId(), pk.getDeviceId()) != null;
    }

    @Override
    public Object getById(Object id) {
	    RestaurantsSessionsId pk = (RestaurantsSessionsId) id;
        RestaurantSessionsEntity entity = getById(pk.getLoginId(), pk.getDeviceId());
        return getVO(entity);
    }

    private RestaurantSessionsEntity getById(Long loginId, String deviceId) {
        return restaurantsSessionsDao.getById(loginId, deviceId);
    }

    @Override
    public Object save(Object obj) {
        if (obj == null) return null;
	    RestaurantSessionsEntity entity = (RestaurantSessionsEntity) getEntity(obj);
        entity = saveEntity(entity);
        return getVO(entity);
    }

    private RestaurantSessionsEntity saveEntity(RestaurantSessionsEntity entity) {
        return restaurantsSessionsDao.save(entity);
    }

    @Override
    public Object getVO(Object obj) {
        if (obj == null) return null;
	    RestaurantSessionsEntity entity = (RestaurantSessionsEntity) obj;
        RestaurantsSessionsVO vo = new RestaurantsSessionsVO();
        vo.setLoginId(entity.getId().getRestaurantId());
        vo.setDeviceId(entity.getId().getDeviceId());
        vo.setSessionToken(entity.getSessionToken());
        vo.setGcmToken(entity.getGcmToken());
        vo.setLoginDatetime((LocalDateTime) entity.getLoginDatetime());
        vo.setLogoutOrInvalidationDatetime((LocalDateTime) entity.getLogoutOrInvalidationDatetime());
        vo.setStatus(entity.getStatus());
        return vo;
    }

    @Override
    public Object getEntity(Object obj) {
        if (obj == null) return null;
        RestaurantsSessionsVO vo = (RestaurantsSessionsVO) obj;
	    RestaurantSessionsEntity entity = getById(vo.getLoginId(), vo.getDeviceId());
        if (entity == null) {
            entity = new RestaurantSessionsEntity();
	        RestaurantsSessionsPK id = new RestaurantsSessionsPK(vo.getLoginId(), vo.getDeviceId());
            entity.setId(id);
        }

        entity.setSessionToken(vo.getSessionToken());
        entity.setGcmToken(vo.getGcmToken());
        entity.setLoginDatetime(vo.getLoginDatetime());
        entity.setLogoutOrInvalidationDatetime(vo.getLogoutOrInvalidationDatetime());
        entity.setStatus(vo.getStatus());
        return entity;
    }

    @Override
    public void createNewUserSession(Long loginId, String deviceId, String authKey, LocalDateTime expiryTime){
	    RestaurantSessionsEntity entity = new RestaurantSessionsEntity();
        RestaurantsSessionsPK id = new RestaurantsSessionsPK(loginId, deviceId);
        entity.setId(id);
        entity.setLoginDatetime(helper.getCurrentTime());
        entity.setLogoutOrInvalidationDatetime(expiryTime);
        entity.setSessionToken(authKey);
        entity.setStatus(Constants.STATUS_ACTIVE);
        saveEntity(entity);
    }

    @Override
    public void updateGcmToken(Long loginId, String deviceId, String gcmToken){
	    restaurantsSessionsDao.updateGcmToken(loginId, deviceId, gcmToken);
    }

    @Override
    public int invalidateActiveSessions(Long loginId){
        return restaurantsSessionsDao.invalidateActiveSessions(loginId, helper.getCurrentTime());
    }

	@Override
    public boolean logout(Long loginId, String deviceId){
        boolean ret = false;
		RestaurantSessionsEntity entity = getById(loginId, deviceId);
        if (entity != null) {
            entity.setStatus(Constants.STATUS_LOGOUT);
            entity.setLogoutOrInvalidationDatetime(helper.getCurrentTime());
            saveEntity(entity);
            ret = true;
        }
        return ret;
    }

    @Override
    public void staleActiveSessions(Long loginId, String deviceId){
	    restaurantsSessionsDao.updateStatus(loginId, deviceId, Constants.STATUS_STALE, helper.getCurrentTime());
    }

    @Override
    public RestaurantsSessionsVO findUserSessionByLoginIdAndDeviceId(Long loginId, String deviceId){
        return (RestaurantsSessionsVO) getById(new RestaurantsSessionsId(loginId, deviceId));
    }

    @Override
    public List<RestaurantsSessionsVO> getActiveSessions(Long loginId){
        List <RestaurantsSessionsVO> list = new ArrayList<>();
        List<RestaurantSessionsEntity> entities = restaurantsSessionsDao.getActiveSessions(loginId);
        if (entities != null && !entities.isEmpty()) {
        	for (RestaurantSessionsEntity entity : entities) { list.add((RestaurantsSessionsVO) getVO(entity)); }
		}
        return list;
    }

	@Override
	public void deleteAnonymous(Long loginId) {
		List<RestaurantSessionsEntity> entities = restaurantsSessionsDao.getAllAspirantSessionsThroughLoginId(loginId);
		for (RestaurantSessionsEntity entity: entities) {
			restaurantsSessionsDao.delete(entity);
		}
	}

}



