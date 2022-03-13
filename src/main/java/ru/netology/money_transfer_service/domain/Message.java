package ru.netology.money_transfer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private final String message;
    private final int id;
}
