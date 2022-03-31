package ru.netology.money_transfer_service.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.money_transfer_service.exception.ConfirmException;

import java.util.Objects;

@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage runtimeExHandler(Exception e) {
        final String message = "Непредвиденная ошибка";
        System.err.println(e.getClass() + ": " + e.getMessage());
        final int id = -1;
        return new ErrorMessage(message, id);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage bindExceptionHandler(BindException e) {
        final String desc = e.getMessage();
        System.err.println(e.getClass() + ": " + desc);
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
        final int id = -1;
        return new ErrorMessage("Перевод указанной суммы недопустим", id);
    }

    @ExceptionHandler(ConfirmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage confirmExceptionHandler(ConfirmException e) {
        final String desc = e.getMessage();
        System.err.println(e.getClass() + ": " + desc);
        final int id = e.getId();
        return new ErrorMessage(desc, id);
    }

    @Data
    @AllArgsConstructor
    static class ErrorMessage {
        private String message;
        private int id;
    }
}
