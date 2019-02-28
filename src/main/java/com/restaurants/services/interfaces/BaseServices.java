package com.restaurants.services.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface BaseServices {

    boolean doesExistId(Object id);

    Object getById(Object id);

    Object save(Object obj);

    Object getVO(Object obj);

    Object getEntity(Object obj);

  
}
