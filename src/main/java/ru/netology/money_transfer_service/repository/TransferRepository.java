package ru.netology.money_transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.netology.money_transfer_service.domain.DateTimeProvider;
import ru.netology.money_transfer_service.domain.Id;
import ru.netology.money_transfer_service.domain.Status;
import ru.netology.money_transfer_service.domain.Transfer;
import ru.netology.money_transfer_service.domain.requests.ConfirmRequest;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;
import ru.netology.money_transfer_service.exception.ConfirmException;
import ru.netology.money_transfer_service.logger.ConsoleLogger;
import ru.netology.money_transfer_service.logger.FileLogger;
import ru.netology.money_transfer_service.logger.ILogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository {
    private final ILogger[] loggers = {new ConsoleLogger(), new FileLogger("logs/logs.log")};
    private static final Map<Integer, Transfer> transferMap = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public Id transfer(TransferRequest request) {
        final Status status = Status.TRANSFER_REQUESTED;
        final int key = id.incrementAndGet();
        final Transfer value = new Transfer(request);
        value.setStatus(status);
        return correctResponse(key, value);
    }

    public Id confirmOperation(ConfirmRequest request) {
        final Status status = Status.VERIFIED;
        final String operationId = request.getOperationId();
        final int key = (operationId != null) ? Integer.parseInt(operationId) : getLastId();
        if (transferMap.containsKey(key)) {
            final Transfer value = transferMap.get(key);
            if (value != null && value.getStatus() != status) {
                value.setModified(DateTimeProvider.getNow());
                value.setStatus(status);
                return correctResponse(key, value);
            }
        }
//        throw new IllegalArgumentException("Указанная транзакция не обнаружена или выполнена ранее");
        throw new ConfirmException("Транзакция не обнаружена или выполнена ранее", key);
    }

    private Id correctResponse(int key, Transfer value) {
        if (!transferMap.containsKey(key))
            transferMap.put(key, value);
        final String message = "ID " + key + ": " + value;
        log(message);
        return new Id(String.valueOf(key));
    }

    public ILogger[] getLoggers() {
        return loggers;
    }

//    public int addNewOperation(String request, Status status) {
//        final int key = id.incrementAndGet();
//        final OperationRecord value = new OperationRecord(request, status);
//        operationMap.put(key, value);
//        return key;
//    }

    private void log(String message) {
        for (ILogger logger : loggers)
            logger.log(false, DateTimeProvider.getNowString() + "\t" + message);
    }

    private int getLastId() {
        return id.get();
    }
}
