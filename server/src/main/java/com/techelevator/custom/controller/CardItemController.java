package com.techelevator.custom.controller;

import com.techelevator.custom.dao.CardItemDao;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.CardItem;
import com.techelevator.custom.model.CardItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/carditem")
public class CardItemController {
    private final CardItemDao cardItemDao;

    public CardItemController(CardItemDao cardItemDao) {
        this.cardItemDao = cardItemDao;
    }

    @GetMapping("/{cardItemId}")
    public CardItemDto getCardItemDto(@PathVariable int cardItemId) {
        try {
            return cardItemDao.getCardItemDtoById(cardItemId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping() // send a carditem as a body
    public void giveCardToUser(@RequestBody CardItem cardItem) {
        try {
            cardItemDao.createCardItem(cardItem);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cardItemId}")
    public void removeCardItem(@PathVariable int cardItemId) {
        try {
            cardItemDao.deleteCardItemById(cardItemId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
