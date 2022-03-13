package ru.netology.money_transfer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Validated
@AllArgsConstructor
public class Amount {
    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer value;
    @NotNull
    @Size(min = 3, max = 4)
    private String currency;
}