package ru.itmo.fake_mts.dto;

import lombok.Data;

@Data
public class AddCardRequest {
    private String pan;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String cardHolderName;
}
