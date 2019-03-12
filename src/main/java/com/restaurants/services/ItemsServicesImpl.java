package com.restaurants.services;

import com.restaurants.db.mariadb.dao.ItemsDao;
import com.restaurants.db.mariadb.entity.ItemsEntity;
import com.restaurants.db.mariadb.entity.pk.ItemsPK;
import com.restaurants.services.interfaces.ItemsServices;
import com.restaurants.utils.SystemHelper;
import com.restaurants.vo.GlobalItemsVO;
import com.restaurants.vo.Ids.GlobalItemsId;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemsServicesImpl implements ItemsServices {
	private final ItemsDao itemsDao;
	private final SystemHelper helper;

	@Autowired
	public ItemsServicesImpl(ItemsDao itemsDao, SystemHelper helper) {
		this.itemsDao = itemsDao;
		this.helper = helper;
	}

	@Override
	public boolean doesExistId(Object id) {
		return  getById(id) != null;
	}

	@Override
	public Object getById(Object id) {
		GlobalItemsId pk = (GlobalItemsId) id;
		ItemsEntity entity = getById(pk.getItemName(), pk.getItemType());
		return getVO(entity);
	}

	private ItemsEntity getById(String itemName, String itemType) {
		return itemsDao.getById(itemName, itemType);
	}

	@Override
	public Object save(Object obj) {
		if (obj == null) return null;
		ItemsEntity entity = (ItemsEntity) getEntity(obj);
		entity = itemsDao.save(entity);
		return getVO(entity);
	}

	@Override
	public Object getVO(Object obj) {
		if (obj == null) return null;
		ItemsEntity entity = (ItemsEntity) obj;
		GlobalItemsVO vo = new GlobalItemsVO();
		GlobalItemsId id = new GlobalItemsId(entity.getId().getItemName(), entity.getId().getItemType());
		vo.setId(id);
		vo.setCreatedTimestamp(entity.getCreatedTimestamp());
		return vo;
	}

	@Override
	public Object getEntity(Object obj) {
		GlobalItemsVO vo = (GlobalItemsVO) obj;
		ItemsEntity entity = getById(vo.getId().getItemName(), vo.getId().getItemType());
		if (entity == null) {
			entity = new ItemsEntity();
			ItemsPK pk = new ItemsPK(vo.getId().getItemName(), vo.getId().getItemType());
			entity.setId(pk);
			entity.setCreatedTimestamp(helper.getCurrentTime());
		}
		return entity;
	}

	@Override
	public List<GlobalItemsVO> getAllItemTypes() {
		List<ItemsEntity> entities = itemsDao.getAllItemTypes();
		List<GlobalItemsVO> vos = new ArrayList<>();
		for (ItemsEntity entity: entities) {
			vos.add((GlobalItemsVO) getVO(entity));
		}
		return vos;
	}

	@Override
	public List<GlobalItemsVO> getItemNamesWithGivenType(String type) {
		List<ItemsEntity> entities = itemsDao.getItemNamesWithGivenType(type);
		List<GlobalItemsVO> vos = new ArrayList<>();
		for (ItemsEntity entity: entities) {
			vos.add((GlobalItemsVO) getVO(entity));
		}
		return vos;
	}
}
