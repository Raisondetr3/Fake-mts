package ru.itmo.fake_mts.exception;

public class AdminRequestNotFoundException extends RuntimeException {
    public AdminRequestNotFoundException(String message) {
        super(message);
    }
}