package com.techelevator.custom.dao;

import com.techelevator.custom.model.StoreItem;
import com.techelevator.custom.model.StoreItemDto;

import java.util.List;

public interface StoreItemDao {
    List<StoreItem> getStoreItems();
    List<StoreItemDto> getStoreItemDtos();
    StoreItem getStoreItemById(int storeItemId);
    StoreItemDto getStoreItemDtoById(int storeItemDtoId);
    List<StoreItemDto> getStoreItemsByUser(int userId);
    StoreItem createStoreItem(StoreItem storeItem);
    StoreItem updateStoreItem(StoreItem storeItem);
    int deleteStoreItemById(int storeItemId);
    boolean isCardItemOnStore(int cardItemId);
}
