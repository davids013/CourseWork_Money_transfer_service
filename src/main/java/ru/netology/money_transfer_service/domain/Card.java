package ru.netology.money_transfer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class Card {
    @NotNull
    @Size(min = 16, max = 16)
    private final String cardNumber;
    @NotNull
    @Size(min = 4, max = 5)
    private final String cardValidTill;
    @NotNull
    @Size(min = 3, max = 3)
    private final transient String cardCVV;
}
