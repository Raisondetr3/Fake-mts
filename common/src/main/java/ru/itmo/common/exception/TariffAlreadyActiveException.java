package ru.itmo.common.exception;

public class TariffAlreadyActiveException extends RuntimeException {
    public TariffAlreadyActiveException(String message) {
        super(message);
    }
}
