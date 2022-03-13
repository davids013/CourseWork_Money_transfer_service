package ru.netology.money_transfer_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.money_transfer_service.domain.*;
import ru.netology.money_transfer_service.domain.requests.ConfirmRequest;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;
import ru.netology.money_transfer_service.logger.ILogger;
import ru.netology.money_transfer_service.service.TransferService;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Validated
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "https://serp-ya.github.io", methods = RequestMethod.POST)
public class TransferController {
    private static TransferService service;
    private static ILogger[] loggers;

    @Autowired
    public TransferController(TransferService service) {
        TransferController.service = service;
        loggers = service.getLoggers();
    }

    @PostMapping("transfer")
//    @ResponseStatus(HttpStatus.OK)
    public Id transferPost(@RequestBody @Valid TransferRequest request) {
        return service.transfer(request);
    }

//    @PostMapping("confirmOperation")
//    @ResponseStatus(HttpStatus.OK)
//    public Id confirmOperationPost(@RequestBody @Valid @Size(min = 14, max = 16) String code) {
//        return service.confirmOperation(code);
//    }

    @PostMapping("confirmOperation")
    public Id confirmOperationPost(@RequestBody @Valid ConfirmRequest request) {
        return service.confirmOperation(request);
    }

    public static ILogger[] getLoggers() {
        return loggers;
    }

    public static int addNewOperation(String request, Status status) {
        return service.addNewOperation(request, status);
    }

    public static OperationRecord getOperationById(int key) {
        return service.getOperationById(key);
    }
}
