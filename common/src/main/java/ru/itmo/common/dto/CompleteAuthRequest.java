package ru.itmo.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CompleteAuthRequest {

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",
            message = "Invalid format phone number"
    )
    private String phoneNumber;

    private String password;
    private String smsCode;
}
