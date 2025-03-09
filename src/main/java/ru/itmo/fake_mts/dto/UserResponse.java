package ru.itmo.fake_mts.dto;

import lombok.*;
import ru.itmo.fake_mts.entity.User;

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
    private String emailBackup;
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
                .emailBackup(user.getEmailBackup())
                .balance(user.getBalance())
                .authMethod(user.getAuthMethod().name())
                .status(user.getStatus().name())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
