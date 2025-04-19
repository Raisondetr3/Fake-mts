package ru.itmo.common.exception;

public class AdminAlreadyGrantedException extends RuntimeException {
    public AdminAlreadyGrantedException(String message) {
        super(message);
    }
}
