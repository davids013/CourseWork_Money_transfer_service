package ru.netology.money_transfer_service.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.netology.money_transfer_service.domain.Amount;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Validated
@AllArgsConstructor
public class TransferRequest {
    @NotNull
    @Pattern(regexp = "\\d{16}")
    private String cardFromNumber;
    @NotNull
    @Pattern(regexp = "[01][0-9]/[2-9][0-9]")
    private transient String cardFromValidTill;
    @NotNull
    @Pattern(regexp = "\\d{3}")
    private transient String cardFromCVV;
    @NotNull
    @Pattern(regexp = "\\d{16}")
    private String cardToNumber;
    @NotNull
    private Amount amount;
}
