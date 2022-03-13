package ru.netology.money_transfer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;

@Data
public class Transfer {
    private String cardFromNumber;
    private String cardToNumber;
    private Amount amount;
    private double fee;

    public Transfer (String cardFromNumber, String cardToNumber, Amount amount) {
        final double feePercentage = 1.0d;
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
        fee = amount.getValue() * feePercentage / 100;
    }

    public Transfer (TransferRequest request) {
        final double feePercentage = 1d;
        cardFromNumber = request.getCardFromNumber();
        cardToNumber = request.getCardToNumber();
        amount = request.getAmount();
        fee = amount.getValue() * feePercentage / 100;
    }
}
