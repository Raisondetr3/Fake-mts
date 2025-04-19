package ru.itmo.common.exception;

public class InvalidInnFormatException extends RuntimeException {
    public InvalidInnFormatException(String message) {
        super(message);
    }
}
