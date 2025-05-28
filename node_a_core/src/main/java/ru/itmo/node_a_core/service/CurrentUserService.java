package ru.itmo.node_a_core.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.common.entity.User;
import ru.itmo.common.exception.UserNotFoundException;
import ru.itmo.common.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;
    private final IdentityService identityService;

    public User getCurrentUserOrThrow() {

        var springAuth = SecurityContextHolder.getContext().getAuthentication();
        if (springAuth != null && springAuth.isAuthenticated()) {
            String phone = springAuth.getName();
            return userRepository.findByPhoneNumber(phone)
                    .orElseThrow(() -> new UserNotFoundException(phone));
        }

        var camAuth = identityService.getCurrentAuthentication();
        if (camAuth != null) {
            String userId = camAuth.getUserId();
            return userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new UserNotFoundException(userId));
        }

        throw new AuthenticationCredentialsNotFoundException("No active user");
    }
}
