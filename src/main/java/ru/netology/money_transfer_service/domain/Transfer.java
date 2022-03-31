package ru.netology.money_transfer_service.domain;

import lombok.Data;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;

import java.time.LocalDateTime;

@Data
public class Transfer {
    private final LocalDateTime created;
    private LocalDateTime modified;
    private final String cardFromNumber;
    private final String cardToNumber;
    private final Amount amount;
    private final double fee;
    private Status status;

    public Transfer(TransferRequest request) {
        final double feePercentage = 1.0d;
        created = DateTimeProvider.getNow();
        modified = created;
        cardFromNumber = request.getCardFromNumber();
        cardToNumber = request.getCardToNumber();
        amount = request.getAmount();
        fee = amount.getValue() * feePercentage / 100;
        status = Status.ERROR_TRANSFER;
    }

    public void setStatus(Status status) {
        modified = DateTimeProvider.getNow();
        this.status = status;
    }
}
