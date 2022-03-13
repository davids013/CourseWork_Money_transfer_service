package ru.netology.money_transfer_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.money_transfer_service.domain.*;
import ru.netology.money_transfer_service.domain.requests.ConfirmRequest;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;
import ru.netology.money_transfer_service.logger.ILogger;
import ru.netology.money_transfer_service.repository.TransferRepository;

@Service
public class TransferService {
    private final TransferRepository repository;

    @Autowired
    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    public Id transfer(TransferRequest request) {
        return repository.transfer(request);
    }

    public Id confirmOperation(ConfirmRequest request) {
        return repository.confirmOperation(request);
    }

//    public Id confirmOperation(String code) {
//        return repository.confirmOperation(code);
//    }

    public ILogger[] getLoggers() {
        return repository.getLoggers();
    }

    public int addNewOperation(String request, Status status) {
        return repository.addNewOperation(request, status);
    }

    public OperationRecord getOperationById(int key) {
        return repository.getOperationById(key);
    }
}
