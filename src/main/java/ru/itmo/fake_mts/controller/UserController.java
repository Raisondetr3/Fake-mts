package ru.itmo.fake_mts.controller;


import ru.itmo.fake_mts.dto.UserPatchRequest;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.dto.AuthRequest;
import ru.itmo.fake_mts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/start-auth")
    public String startAuth(@RequestParam String phoneNumber) {
        return userService.startAuth(phoneNumber);
    }

    @PostMapping("/complete-auth")
    public String completeAuth(@RequestParam String phoneNumber, @RequestBody AuthRequest request) {
        return userService.completeAuth(phoneNumber, request);
    }

    @PatchMapping("/{userId}")
    public User patchUser(@PathVariable Long userId, @RequestBody UserPatchRequest patch) {
        return userService.patchUser(userId, patch);
    }

    @PutMapping("/{userId}/auth-method")
    public User changeAuthMethod(@PathVariable Long userId,
                                 @RequestParam AuthMethod newMethod,
                                 @RequestParam(required = false) String newPassword) {
        return userService.changeAuthMethod(userId, newMethod, newPassword);
    }
}

