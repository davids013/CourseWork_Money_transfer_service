package ru.netology.money_transfer_service.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.netology.money_transfer_service.domain.Amount;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
@AllArgsConstructor
public class TransferRequest {
    @NotNull
    @Size(min = 16, max = 16)
    private String cardFromNumber;
    @NotNull
    @Size(min = 5, max = 5)
    private transient String cardFromValidTill;
    @NotNull
    @Size(min = 3, max = 3)
    private transient String cardFromCVV;
    @NotNull
    @Size(min = 16, max = 16)
    private String cardToNumber;
    @NotNull
    private Amount amount;
}
