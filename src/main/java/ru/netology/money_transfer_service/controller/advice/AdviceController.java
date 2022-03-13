package ru.netology.money_transfer_service.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.money_transfer_service.controller.TransferController;
import ru.netology.money_transfer_service.domain.DateTimeProvider;
import ru.netology.money_transfer_service.domain.Status;
import ru.netology.money_transfer_service.logger.ILogger;

import java.util.Objects;

@RestControllerAdvice
public class AdviceController {
    private final ILogger[] loggers = TransferController.getLoggers();

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage runtimeExHandler(Exception e) {
        final String message = "Непредвиденная ошибка";
        System.err.println(e.getClass() + ": " + e.getMessage());
        final int id = TransferController.addNewOperation("|" + message + "|", Status.ERROR_TRANSFER);
        return new ErrorMessage(message, id);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorMessage metArgNotValidExHandler(MethodArgumentNotValidException e) {
//        final String desc = e.getMessage();
//        final String target = "default message [";
//        final String request = Objects.requireNonNull(e.getTarget()).toString();
//        final int id = TransferController.addNewOperation(request, Status.ERROR_INPUT_DATA);
//        System.err.println(e.getClass() + ": " + desc);
//        log("ID " + id + ": " + TransferController.getOperationById(id));
//        final int index = desc.lastIndexOf(target) + target.length();
//        return new ErrorMessage("Введены некорректные данные карт", id);
//    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage bindExceptionHandler(BindException e) {
        final String desc = e.getMessage();
//        final String target = "default message [";
//        final int id = TransferController.addNewOperation(request, Status.ERROR_INPUT_DATA);
        System.err.println(e.getClass() + ": " + desc);
//        log("ID " + id + ": " + TransferController.getOperationById(id));
        final int id = -1;
        final String target = Objects.requireNonNull(e.getTarget()).toString();
        if (target.contains("onfirm")) {
            return new ErrorMessage("Некорректный код подтверждения", id);
        } else if (target.contains("ransferRequest")) {
            return new ErrorMessage("Некорректные данные карт", id);
        } else return new ErrorMessage("Введены некорректные данные", id);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage httpMesNotReadExHandler(HttpMessageNotReadableException e) {
        final String desc = e.getMessage();
        System.err.println(desc);
        final int id = -2;
        return new ErrorMessage("Перевод указанной суммы недопустим", id);
    }

    private void log(String message) {
        for (ILogger logger : loggers)
            logger.log(true, DateTimeProvider.getNow() + "\t" + message);
    }

    @Data
    @AllArgsConstructor
    static class ErrorMessage {
        private String message;
        private int id;
    }
}
