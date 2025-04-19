package ru.itmo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SmsMessage implements Serializable {
    private String phoneNumber;
    private String smsCode;
}
