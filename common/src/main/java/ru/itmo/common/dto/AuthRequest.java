package ru.itmo.common.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRequest {
    private String password;
    private String smsCode;
}

