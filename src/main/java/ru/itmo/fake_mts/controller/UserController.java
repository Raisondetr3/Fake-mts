package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.dto.*;
import ru.itmo.fake_mts.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/start-auth")
    public StartAuthResponse startAuth(@RequestParam String phoneNumber) {
        return userService.startAuth(phoneNumber);
    }

    @PostMapping("/complete-auth")
    public AuthCompleteResponse completeAuth(@RequestParam String phoneNumber, @RequestBody AuthRequest request) {
        return userService.completeAuth(phoneNumber, request);
    }

    @PatchMapping
    public UserResponse patchUser(@RequestBody UserPatchRequest patch) {
        return userService.patchUser(patch);
    }

    @PutMapping("/me/auth-method")
    public UserIdResponse changeAuthMethod(@RequestBody ChangeAuthMethodRequest dto) {
        return userService.changeAuthMethod(dto);
    }
}
