package ru.netology.money_transfer_service.logger;

@FunctionalInterface
public interface ILogger {
    void log(boolean isError, String message);
}
