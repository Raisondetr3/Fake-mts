package ru.itmo.fake_mts.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserPatchRequest {
    private String fullName;

    @Pattern(regexp = "\\d{11}", message = "Incorrect СНИЛС format (11 digits)")
    private String snils;

    @Pattern(regexp = "\\d{11}", message = "Incorrect ИНН format (12 digits)")
    private String inn;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Incorrect email format")
    private String emailBackup;
}
