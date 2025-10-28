package com.techelevator.custom.controller;

import com.techelevator.custom.dao.CardDao;
import com.techelevator.custom.dao.ItemDao;
import com.techelevator.custom.dao.UserDao;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin
@RestController
public class ItemController {
    private final ItemDao itemDao;
    private final CardDao cardDao;
    private final UserDao userDao;
    private Map<Integer, Long> lastPullTime = new ConcurrentHashMap<>();
    public ItemController(ItemDao itemDao, CardDao cardDao, UserDao userDao) {
        this.itemDao = itemDao;
        this.cardDao = cardDao;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mycards/unique")
    public List<UniqueItemDto> getMyUniqueCards(Principal principal) {
        try {
            int userId = userDao.getUserByUsername(principal.getName()).getId();
            return itemDao.getUniqueItemDtosByUser(userId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/pull")
    public List<Card> pullCards(Principal principal) {
        // in a full implementation there would be checks i.e. how many times per day you can pull
        Random random = new Random();
        List<Card> cards = new ArrayList<>();
        int cardsToPull = 5; // default # of cards to pull
        try {
            int userId = userDao.getUserByUsername(principal.getName()).getId();

            // prevent spam/double pulls
            long now = System.currentTimeMillis();
            long lastTime = lastPullTime.getOrDefault(userId, 0L);
            long cooldown = 3000; // 3 seconds cooldown
            if (now - lastTime < cooldown) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Multiple requests sent too quickly. Please wait before pulling again.");
            }
            lastPullTime.put(userId, now);

            int totalCards = cardDao.getCardsCount();
            while (cardsToPull > 0) {
                int newCardId = random.nextInt(totalCards) + 1;
                Item newItem = new Item(userId, newCardId, null);
                cards.add(cardDao.getCardById(itemDao.createItem(newItem).getCardId()));
                cardsToPull--;
            }
            return cards;
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

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/items/purchase/{itemId}") // holy mackerel this is not structured well
    public Item purchase(@PathVariable int itemId, @Valid @RequestBody Item item, Principal principal) {
        item.setItemId(itemId);
        User buyer = userDao.getUserByUsername(principal.getName());
        Item itemToUpdate = itemDao.getItemById(itemId);

        // if the user has enough funds, only the user id and price are changing, and the new price is 0
        if (buyer.getBalance().compareTo(item.getPrice()) >= 0 && isOnlyTheUserIdBeingUpdated(itemToUpdate, item)) {
            BigDecimal itemPrice = item.getPrice();
            // subtract item price from current user's balance
            try {
                BigDecimal buyerBalance = buyer.getBalance();
                BigDecimal newBuyerBalance = buyerBalance.subtract(itemPrice);
                User updatedBuyer = new User(buyer.getId(), buyer.getUsername(), buyer.getHashedPassword(), buyer.getRole(), newBuyerBalance);
                userDao.updateUser(updatedBuyer);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating buyer balance: " + e.getMessage());
            }
            // add item price to the previous owner's balance
            try {
                User seller = userDao.getUserById(itemToUpdate.getUserId());
                BigDecimal sellerBalance = seller.getBalance();
                BigDecimal newSellerBalance = sellerBalance.add(itemPrice);
                User updatedSeller = new User(seller.getId(), seller.getUsername(), seller.getHashedPassword(), seller.getRole(), newSellerBalance);
                userDao.updateUser(updatedSeller);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating seller balance: " + e.getMessage());
            }
            // update the item (set the price to 0 and change the item's owner to the current user)
            try {
                Item newItem = new Item(item.getItemId(), item.getUserId(), item.getCardId(), new BigDecimal("0.00"));
                return itemDao.updateItem(newItem);
            } catch (DaoException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating item owner: " + e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough funds");
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

    private boolean isOnlyTheUserIdBeingUpdated(Item currentItem, Item newItem) {
        return currentItem.getItemId() == newItem.getItemId()
                && currentItem.getCardId() == newItem.getCardId();
    }

    private boolean isUserAdmin(User user) {
        return user.getRole().equals("ROLE_ADMIN");
    }
}
