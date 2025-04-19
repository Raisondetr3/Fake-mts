package ru.itmo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminMailMessage {
    private Long userId;
    private String email;
    private String fullName;
}
