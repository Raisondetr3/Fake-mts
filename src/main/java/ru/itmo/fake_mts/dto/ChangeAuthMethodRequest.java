package ru.itmo.fake_mts.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.fake_mts.entity.enums.AuthMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAuthMethodRequest {
    @NotNull(message = "New authentication method is required")
    private AuthMethod newMethod;

    private String newPassword;
}

