package com.restaurants.services.interfaces;

import com.restaurants.vo.RestaurantsSessionsVO;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantsSessionsServices extends BaseServices  {

    void createNewUserSession(Long loginId, String deviceId, String authKey, LocalDateTime expiryTime);

    void updateGcmToken(Long loginId, String deviceId, String gcmToken);

    int invalidateActiveSessions(Long loginId);

    void staleActiveSessions(Long loginId, String deviceId);

    RestaurantsSessionsVO findUserSessionByLoginIdAndDeviceId(Long loginId, String deviceId);

    boolean logout(Long loginId, String deviceId);

	List<RestaurantsSessionsVO> getActiveSessions(Long loginId);

	void deleteAnonymous(Long loginId);
}
