package ru.itmo.fake_mts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.fake_mts.entity.AuthMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAuthMethodRequest {
    private AuthMethod newMethod;
    private String newPassword;
}

