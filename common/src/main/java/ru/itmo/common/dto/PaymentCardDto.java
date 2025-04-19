package ru.itmo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.common.entity.PaymentCard;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCardDto {
    private Long id;
    private String panMasked;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String cardHolderName;

    public static PaymentCardDto fromEntity(PaymentCard card) {
        return PaymentCardDto.builder()
                .id(card.getId())
                .panMasked(card.getPanMasked())
                .expiryMonth(card.getExpiryMonth())
                .expiryYear(card.getExpiryYear())
                .cardHolderName(card.getCardHolderName())
                .build();
    }
}

