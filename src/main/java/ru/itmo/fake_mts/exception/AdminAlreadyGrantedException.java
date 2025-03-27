package ru.itmo.fake_mts.exception;

public class AdminAlreadyGrantedException extends RuntimeException {
    public AdminAlreadyGrantedException(String message) {
        super(message);
    }
}
