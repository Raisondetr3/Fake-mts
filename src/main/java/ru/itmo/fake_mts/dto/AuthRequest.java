package ru.itmo.fake_mts.dto;


import lombok.Data;

@Data
public class AuthRequest {
    private String password;
    private String smsCode;
}

