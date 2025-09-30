package com.techelevator.custom.dao;

import com.techelevator.custom.model.Item;
import com.techelevator.custom.model.ItemDto;

import java.util.List;

public interface ItemDao {
    List<Item> getAllItems();
    List<ItemDto> getAllItemDtos();
    Item getItemById(int itemId);
    ItemDto getItemDtoById(int itemId);
    List<ItemDto> getItemDtosByUser(int userId);
    List<ItemDto> getItemDtosOnStore();
    Item createItem(Item item);
    Item updateItem(Item item);
    int deleteItemById(int itemId);
}
