package ru.itmo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRequestMessage {
    private String email;
    private String message;
}
