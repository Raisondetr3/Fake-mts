package ru.itmo.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsMessage implements Serializable {
    private String phoneNumber;
    private String smsCode;
}
