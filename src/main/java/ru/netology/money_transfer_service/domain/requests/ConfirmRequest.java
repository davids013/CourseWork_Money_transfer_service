package ru.netology.money_transfer_service.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequest {
//    @NotNull
//    private final String operationId;
    @NotNull
    @Size(min = 4, max = 6)
    private String code;
}
