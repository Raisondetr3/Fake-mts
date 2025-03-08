package ru.itmo.fake_mts.exception;

public class InvalidInnFormatException extends RuntimeException {
    public InvalidInnFormatException(String message) {
        super(message);
    }
}
