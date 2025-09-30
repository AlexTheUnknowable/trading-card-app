package com.techelevator.custom.controller;

import com.techelevator.custom.dao.CardItemDao;
import com.techelevator.custom.dao.StoreItemDao;
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
@RequestMapping("/store-old")
@PreAuthorize("isAuthenticated()")
public class StoreItemController {
    private final StoreItemDao siDao;
    private final UserDao userDao;
    private final CardItemDao ciDao;

    public StoreItemController(StoreItemDao siDao, UserDao userDao, CardItemDao ciDao) {
        this.siDao = siDao;
        this.userDao = userDao;
        this.ciDao = ciDao;
    }

    @GetMapping
    public List<StoreItemDto> list(@RequestParam(required = false) String name) {
        try {
            List<StoreItemDto> allItems = siDao.getStoreItemDtos();
            if (name == null) {
                return allItems;
            } else {
                List<StoreItemDto> matchingItems = new ArrayList<>();
                for (StoreItemDto item : allItems) {
                    if (item.getName().contains(name)) {
                        matchingItems.add(item);
                    }
                }
                return matchingItems;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get store items: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public StoreItemDto get(@PathVariable int id) {
        try {
            return siDao.getStoreItemDtoById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get store item: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mycards")
    public List<StoreItemDto> getMyCards(Principal principal) {
        try {
            User user = userDao.getUserByUsername(principal.getName());
            int userId = user.getId();
            return siDao.getStoreItemsByUser(userId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StoreItem add(@Valid @RequestBody StoreItem si, Principal principal) {
        // if the user owns the card that they're trying to make a store item out of, they can create the new store item. otherwise not allowed
        int userId = getUserIdFromPrincipal(principal);
        int cardItemId = si.getCardItemId();
        if (ciDao.userOwnsCard(userId, cardItemId)) { // if the user owns this card item
            if (!siDao.isCardItemOnStore(cardItemId)) { // if the card item isn't already on the store
                try {
                    // TODO: remove the card from their account? or maybe set the card as "up for sale" somehow? eh its outside the project scope
                    return siDao.createStoreItem(si);
                } catch (DaoException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not create store item: " + e.getMessage());
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This card is already on the store.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not own this card, and cannot put it up for sale.");
        }
    }

    @PutMapping("/{id}") // this is unused but i'm leaving it here in case i come back to it in the future
    public StoreItem update(@PathVariable int id, @Valid @RequestBody StoreItem si, Principal principal) {
        si.setStoreItemId(id);
        // if the store item was created by the user, they can update it. otherwise not allowed
        int userId = getUserIdFromPrincipal(principal);
        CardItem cardItem = ciDao.getCardItemById(si.getCardItemId());
        int ownerId = cardItem.getUserId();
        if (ownerId == userId) { // if user owns the store item
            if (si.getCardItemId() == siDao.getStoreItemById(id).getCardItemId()) { // if id of the card item being posted is the same as the one being updated
                try {
                    return siDao.updateStoreItem(si);
                } catch (DaoException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store item not found");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Card item ID to update & card item ID sent in body do not match.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not own this store item, and cannot update it.");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, Principal principal) {
        // if the store item was created by the user, or the user is an admin, they can delete it. otherwise not allowed
        int userId = getUserIdFromPrincipal(principal);
        StoreItemDto storeItemDto = siDao.getStoreItemDtoById(id);
        int ownerId = storeItemDto.getUserId();
        if (ownerId == userId || isUserAdmin(principal)) {
            try {
                siDao.deleteStoreItemById(id);
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store item not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to delete this store item.");
        }
    }

    private int getUserIdFromPrincipal(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return user.getId();
    }

    private boolean isUserAdmin(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        if (user.getRole().equals("ROLE_ADMIN")) {
            return true;
        } else {
            return false;
        }
    }

}
