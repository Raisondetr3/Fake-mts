package ru.itmo.common.dto;

import lombok.*;
import ru.itmo.common.entity.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String phoneNumber;
    private String email;
    private String fullName;
    private String snils;
    private String inn;
    private Double balance;
    private String authMethod;
    private String status;
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .snils(user.getSnils())
                .inn(user.getInn())
                .balance(user.getBalance())
                .authMethod(user.getAuthMethod().name())
                .status(user.getStatus().name())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
