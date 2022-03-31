package ru.netology.money_transfer_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.money_transfer_service.domain.Id;
import ru.netology.money_transfer_service.domain.requests.ConfirmRequest;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;
import ru.netology.money_transfer_service.service.TransferService;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "https://serp-ya.github.io", methods = RequestMethod.POST)
public class TransferController {
    private static TransferService service;

    @Autowired
    public TransferController(TransferService service) {
        TransferController.service = service;
    }

    @PostMapping("transfer")
    @ResponseStatus(HttpStatus.OK)
    public Id transferPost(@RequestBody @Valid TransferRequest request) {
        return service.transfer(request);
    }

    @PostMapping("confirmOperation")
    @ResponseStatus(HttpStatus.OK)
    public Id confirmOperationPost(@RequestBody @Valid ConfirmRequest request) {
        return service.confirmOperation(request);
    }
}
