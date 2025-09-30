package com.techelevator.custom.controller;

import com.techelevator.custom.dao.ItemDao;
import com.techelevator.custom.dao.UserDao;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class ItemController {
    private final ItemDao itemDao;
    private final UserDao userDao;
    public ItemController(ItemDao itemDao, UserDao userDao) {
        this.itemDao = itemDao;
        this.userDao = userDao;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/items")
    public List<ItemDto> list(@RequestParam(required = false) String name) {
        try {
            List<ItemDto> items = itemDao.getAllItemDtos();
            if (name == null) {
                return items;
            } else {
                List<ItemDto> matchingItems = new ArrayList<>();
                for (ItemDto item : items) {
                    if (item.getName().contains(name)) {
                        matchingItems.add(item);
                    }
                }
                return matchingItems;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get items: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/items/{id}")
    public ItemDto get(@PathVariable int id) {
        try {
            return itemDao.getItemDtoById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get item: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mycards")
    public List<ItemDto> getMyCards(Principal principal) {
        try {
            int userId = userDao.getUserByUsername(principal.getName()).getId();
            return itemDao.getItemDtosByUser(userId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/store")
    public List<ItemDto> listStore() {
        try {
            return itemDao.getItemDtosOnStore();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get items: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/items")
    public void add(@Valid @RequestBody Item item) { // Creates an item and gives it to specified user
        try {
            itemDao.createItem(item);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/items/{itemId}")
    public Item update(@PathVariable int itemId, @Valid @RequestBody Item item, Principal principal) {
        item.setItemId(itemId);
        User user = userDao.getUserByUsername(principal.getName());
        Item itemToUpdate = itemDao.getItemById(itemId);
        // if A) the item belongs to the user, and they are only changing the price, or B) the user is an admin, do it
        if (userOwnsItem(user, itemToUpdate) || isUserAdmin(user)) {
            if (isOnlyThePriceBeingUpdated(itemToUpdate, item) || isUserAdmin(user)) {
                try {
                    return itemDao.updateItem(item);
                } catch (DaoException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is only allowed to change the item's price.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not own this item, and cannot update it.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{itemId}")
    public void delete(@PathVariable int itemId) { // Deletes item
        try {
            itemDao.deleteItemById(itemId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private boolean userOwnsItem(User user, Item itemToUpdate) {
        return user.getId() == itemToUpdate.getUserId();
    }

    private boolean isOnlyThePriceBeingUpdated(Item currentItem, Item newItem) {
        return currentItem.getItemId() == newItem.getItemId()
                && currentItem.getUserId() == newItem.getUserId()
                && currentItem.getCardId() == newItem.getCardId();
    }

    private boolean isUserAdmin(User user) {
        return user.getRole().equals("ROLE_ADMIN");
    }
}
