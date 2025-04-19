package ru.itmo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    private Long id;
    private String phoneNumber;
    private String fullName;
    private String adminRequestStatus;
}