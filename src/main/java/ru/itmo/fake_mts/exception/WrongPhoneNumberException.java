package ru.itmo.fake_mts.exception;

public class WrongPhoneNumberException extends RuntimeException {
    public WrongPhoneNumberException(String message) {
        super(message);
    }
}

