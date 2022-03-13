package ru.netology.money_transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.netology.money_transfer_service.domain.*;
import ru.netology.money_transfer_service.domain.requests.ConfirmRequest;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;
import ru.netology.money_transfer_service.logger.ConsoleLogger;
import ru.netology.money_transfer_service.logger.FileLogger;
import ru.netology.money_transfer_service.logger.ILogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository {
    private final ILogger logger = new FileLogger("logs/logs.log");
    private final ILogger[] loggers = {new ConsoleLogger(), new FileLogger("logs/logs.log")};
    private static final Map<Integer, OperationRecord> operationMap = new ConcurrentHashMap<>();
//    private static final Map<Integer, OperationRecord> transferMap = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public Id transfer(TransferRequest request) {
        final Status status = Status.TRANSFER_REQUESTED;
        final int key = id.incrementAndGet();
        final OperationRecord value = new OperationRecord(request.toString(), status);
        return correctResponse(key, value, status);
    }

    public Id confirmOperation(ConfirmRequest request) {
        final Status status = Status.VERIFIED;
        final int key = id.incrementAndGet();
        final OperationRecord value = new OperationRecord(request.toString(), status);
        return correctResponse(key, value, status);
    }

//    public Id confirmOperation(String code) {
//        final Status status = Status.VERIFIED;
//        final int key = id.incrementAndGet();
//        final OperationRecord value = new OperationRecord(code, status);
//        return correctResponse(key, value, status);
//    }

    private Id correctResponse(int key, OperationRecord value, Status status) {
        operationMap.put(key, value);
        final String message = "ID " + key + ": " + value;
        log(message);
        return new Id(String.valueOf(key));
    }

    public ILogger getLogger() {
        return logger;
    }

    public ILogger[] getLoggers() {
        return loggers;
    }

    public int addNewOperation(String request, Status status) {
        final int key = id.incrementAndGet();
        final OperationRecord value = new OperationRecord(request, status);
        operationMap.put(key, value);
        return key;
    }

    public OperationRecord getOperationById(int key) {
        return operationMap.getOrDefault(key, null);
    }

    private void log(String message) {
        for (ILogger logger : loggers)
            logger.log(false, DateTimeProvider.getNow() + "\t" + message);
    }
}
