package ru.itmo.fake_mts.exception;

import org.springframework.security.core.AuthenticationException;

public class WrongCredentialsException extends AuthenticationException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
