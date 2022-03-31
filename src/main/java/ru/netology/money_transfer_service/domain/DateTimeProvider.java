package ru.netology.money_transfer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class DateTimeProvider {
    private static String delimeter = "_";
    private static String datePattern = "yyyy-MM-dd";
    private static String timePattern = "HH:mm:ss.S";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern + delimeter + timePattern);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);

    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    public static String getNowString() {
        return getNow().format(dateTimeFormatter);
    }

    public static String getNowDateString() {
        return LocalDate.now().format(dateFormatter);
    }

    public static String getNowTimeString() {
        return LocalTime.now().format(timeFormatter);
    }

    public static JsonDateTime getNowJson() {
        return new JsonDateTime(getNowDateString(), getNowTimeString());
    }

    public static String getNowWithFormat(String customFormat) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customFormat);
        return getNow().format(formatter);
    }

    public static String timeToString(LocalDateTime ldt) {
        return ldt.format(dateTimeFormatter);
    }

    public static void setDelimeter(String pattern) {
        delimeter = pattern;
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern + delimeter + timePattern);
    }

    public static void setTimePattern(String pattern) {
        timePattern = pattern;
        timeFormatter = DateTimeFormatter.ofPattern(timePattern);
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern + delimeter + timePattern);
    }

    public static void setDatePattern(String pattern) {
        datePattern = pattern;
        dateFormatter = DateTimeFormatter.ofPattern(datePattern);
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern + delimeter + timePattern);
    }

    @Data
    @AllArgsConstructor
    private static class JsonDateTime {
        final String date;
        final String time;
    }
}
