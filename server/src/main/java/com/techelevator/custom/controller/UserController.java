package com.techelevator.custom.controller;

import com.techelevator.custom.dao.*;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.CardItemDto;
import com.techelevator.custom.model.ItemDto;
import com.techelevator.custom.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserDao userDao;
    private final ItemDao itemDao;

    public UserController(UserDao userDao, ItemDao itemDao) {
        this.userDao = userDao;
        this.itemDao = itemDao;
    }

    @GetMapping
    public List<User> list() {
        try {
            return userDao.getUsers();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get cards: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        try {
            return userDao.getUserById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get card: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/cards")
    public List<ItemDto> getUserCards(@PathVariable int userId) {
        try {
            // get all items with the given userId
            return itemDao.getItemDtosByUser(userId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
