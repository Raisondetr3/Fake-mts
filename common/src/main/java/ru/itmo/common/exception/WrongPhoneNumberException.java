package ru.itmo.common.exception;

public class WrongPhoneNumberException extends RuntimeException {
    public WrongPhoneNumberException(String message) {
        super(message);
    }
}

