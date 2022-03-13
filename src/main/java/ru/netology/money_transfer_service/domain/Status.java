package ru.netology.money_transfer_service.domain;

public enum Status {
    TRANSFER_REQUESTED,
    VERIFIED,
    ERROR_INPUT_DATA,
    ERROR_TRANSFER
}
