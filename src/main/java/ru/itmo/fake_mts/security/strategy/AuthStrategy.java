package ru.itmo.fake_mts.security.strategy;


import ru.itmo.fake_mts.dto.AuthRequest;
import ru.itmo.fake_mts.dto.CompleteAuthRequest;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;

public interface AuthStrategy {
    AuthMethod getAuthMethod();
    boolean authenticate(User user, CompleteAuthRequest authRequest);
}