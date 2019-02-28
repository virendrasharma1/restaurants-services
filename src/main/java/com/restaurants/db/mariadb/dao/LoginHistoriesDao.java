package com.restaurants.db.mariadb.dao;

import com.restaurants.db.mariadb.entity.LoginHistoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoriesDao extends JpaRepository<LoginHistoriesEntity, Long> {

    @Query("SELECT l FROM LoginHistoriesEntity l WHERE l.loginHistoryId = :loginHistoryId")
    LoginHistoriesEntity getById(@Param("loginHistoryId") Long loginHistoryId);
}
