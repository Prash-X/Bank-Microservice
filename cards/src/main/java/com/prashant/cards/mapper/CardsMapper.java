package com.prashant.cards.mapper;

import com.prashant.cards.dto.CardsDto;
import com.prashant.cards.entity.Cards;

public class CardsMapper {

    public static Cards maptoCards(CardsDto cardsDto, Cards cards){
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        return cards;
    }

    public static CardsDto maptoCardsDto(CardsDto cardsDto, Cards cards){
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        return cardsDto;
    }
}
