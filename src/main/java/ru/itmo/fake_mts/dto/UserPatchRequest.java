package ru.itmo.fake_mts.dto;

import lombok.Data;

@Data
public class UserPatchRequest {
    private String fullName;
    private String snils;
    private String inn;
    private String emailBackup;
}
