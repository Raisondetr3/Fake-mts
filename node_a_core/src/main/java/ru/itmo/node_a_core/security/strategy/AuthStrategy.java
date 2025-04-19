package ru.itmo.node_a_core.security.strategy;


import ru.itmo.common.dto.CompleteAuthRequest;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.common.entity.User;

public interface AuthStrategy {
    AuthMethod getAuthMethod();
    boolean authenticate(User user, CompleteAuthRequest authRequest);
}