package ru.netology.money_transfer_service.logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsoleLogger implements ILogger {
    public void log(boolean isError, String message) {
        if (isError) {
            System.err.println(message);
        } else
            System.out.println(message);
    }
}
