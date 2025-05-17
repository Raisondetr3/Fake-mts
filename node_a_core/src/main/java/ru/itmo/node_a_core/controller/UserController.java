package ru.itmo.node_a_core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.common.dto.*;
import ru.itmo.node_a_core.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/start-auth")
    public StartAuthResponse startAuth(@RequestBody @Valid StartAuthRequest request) {
        return userService.startAuth(request.getPhoneNumber());
    }

    @PostMapping("/complete-auth")
    public AuthCompleteResponse completeAuth(@RequestBody @Valid CompleteAuthRequest request) {
        return userService.completeAuth(request);
    }

    @PatchMapping
    public UserResponse patchUser(@RequestBody @Valid UserPatchRequest patch) {
        return userService.patchUser(patch);
    }

    @PutMapping("/me/auth-method")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeAuthMethod(@RequestBody ChangeAuthMethodRequest dto) {
        userService.changeAuthMethod(dto);
    }
}
