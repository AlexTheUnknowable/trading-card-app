package com.techelevator.custom.controller;

import com.techelevator.custom.dao.CardDao;
import com.techelevator.custom.dao.CardItemDao;
import com.techelevator.custom.dao.UserDao;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Card;
import com.techelevator.custom.model.CardItem;
import com.techelevator.custom.model.CardItemDto;
import com.techelevator.custom.model.User;
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
@RequestMapping("/cards")
public class CardController {
    private final CardDao cardDao;
    private final UserDao userDao;
    private final CardItemDao cardItemDao;

    public CardController(CardDao cardDao, UserDao userDao, CardItemDao cardItemDao) {
        this.cardDao = cardDao;
        this.userDao = userDao;
        this.cardItemDao = cardItemDao;
    }

    @GetMapping
    public List<Card> list() {
        try {
            return cardDao.getCards();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get cards: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Card get(@PathVariable int id) {
        try {
            return cardDao.getCardById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not get card: " + e.getMessage());
        }
    }

//    moved to ItemController
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/mycards")
//    public List<CardItemDto> getMyCards(Principal principal) {
//        try {
//            User user = userDao.getUserByUsername(principal.getName());
//            int userId = user.getId();
//            return cardItemDao.getCardItemsByUser(userId);
//        } catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Card add(@Valid @RequestBody Card card) {
        try {
            return cardDao.createCard(card);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not create card: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Card update(@PathVariable int id, @Valid @RequestBody Card card) {
        card.setId(id);
        try {
            return cardDao.updateCard(card);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            cardDao.deleteCardById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
    }

}
