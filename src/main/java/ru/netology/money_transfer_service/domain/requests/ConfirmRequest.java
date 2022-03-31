package ru.netology.money_transfer_service.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequest {
    private String operationId;
    @NotNull
    @Pattern(regexp = "\\d{4}")
    private String code;
}
