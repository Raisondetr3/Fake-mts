package ru.itmo.fake_mts.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.dto.*;
import ru.itmo.fake_mts.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/start-auth")
    public StartAuthResponse startAuth(
            @RequestParam
            @NotBlank(message = "Phone number is required\n")
            @Pattern(
                    regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",
                    message = "Invalid format phone number"
            )
            String phoneNumber
    ) {
        return userService.startAuth(phoneNumber);
    }

    @PostMapping("/complete-auth")
    public AuthCompleteResponse completeAuth(
            @RequestParam
            @NotBlank(message = "Phone number is required")
            @Pattern(
                    regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",
                    message = "Invalid format phone number"
            )
            String phoneNumber,
            @RequestBody @Valid AuthRequest request
    ) {
        return userService.completeAuth(phoneNumber, request);
    }

    @PatchMapping
    public UserResponse patchUser(@RequestBody UserPatchRequest patch) {
        return userService.patchUser(patch);
    }

    @PutMapping("/me/auth-method")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeAuthMethod(@RequestBody ChangeAuthMethodRequest dto) {
        userService.changeAuthMethod(dto);
    }
}
