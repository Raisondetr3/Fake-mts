package ru.itmo.fake_mts.controller;


import ru.itmo.fake_mts.dto.*;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{userId}")
    public UserResponse patchUser(@PathVariable Long userId, @RequestBody UserPatchRequest patch) {
        return userService.patchUser(userId, patch);
    }

    @PutMapping("/{userId}/auth-method")
    public UserResponse changeAuthMethod(@PathVariable Long userId,
                                         @RequestParam AuthMethod newMethod,
                                         @RequestParam(required = false) String newPassword) {
        return userService.changeAuthMethod(userId, newMethod, newPassword);
    }
}

