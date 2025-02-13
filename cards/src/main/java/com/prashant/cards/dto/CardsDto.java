package com.prashant.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardsDto {

    @NotEmpty(message = "Mobile Number can not be Null or Empty")
    @Pattern(regexp = "([0-9]{10})", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Card Number can not be null or empty")
    @Pattern(regexp = "([0-9]{12})",message = "Card Number must be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "CardType can not be null or empty")
    private String cardType;

    @Positive(message = "Total card Limit should be greater than zero")
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private int amountUsed;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private int availableAmount;
}
