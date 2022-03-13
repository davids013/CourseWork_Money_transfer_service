package ru.netology.money_transfer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationRecord {
    private String request;
    private Status status;
}
