package ru.itmo.fake_mts.dto;

import lombok.Data;

@Data
public class UnifiedLoginRequest {
    private String phoneNumber;
    private String smsCode;
}
