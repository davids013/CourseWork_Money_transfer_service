package ru.netology.money_transfer_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.money_transfer_service.domain.DateTimeProvider;
import ru.netology.money_transfer_service.domain.Id;
import ru.netology.money_transfer_service.domain.requests.ConfirmRequest;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;
import ru.netology.money_transfer_service.exception.ConfirmException;
import ru.netology.money_transfer_service.repository.TransferRepository;

import java.time.LocalDateTime;

@Service
public class TransferService {
    private final TransferRepository repository;

    @Autowired
    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    public Id transfer(TransferRequest request) {
        final LocalDateTime now = DateTimeProvider.getNow();
        final int year = now.getYear();
        final int month = now.getMonthValue();
        final String cardFromValidTill = request.getCardFromValidTill();
        final int index = cardFromValidTill.indexOf("/");
        final int cardFromYear = Integer.parseInt(cardFromValidTill.substring(index + 1)) + 2000;
        final int cardFromMonth = Integer.parseInt(cardFromValidTill.substring(0, index));
        if (year > cardFromYear || cardFromMonth > 12 || (year == cardFromYear && month > cardFromMonth)) {
//            throw new IllegalArgumentException("Недопустимый срок действия карты списания (" + cardFromValidTill + ")");
            throw new ConfirmException("Недопустимый срок действия карты списания (" + cardFromValidTill + ")", -1);
        }
        return repository.transfer(request);
    }

    public Id confirmOperation(ConfirmRequest request) {
        return repository.confirmOperation(request);
    }
}
