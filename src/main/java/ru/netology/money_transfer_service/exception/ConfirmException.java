package ru.netology.money_transfer_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmException extends RuntimeException {
    private final String message;
    private final int id;
}
