package ru.itmo.fake_mts.dto;

import ru.itmo.fake_mts.entity.Tariff;

public record TariffPresentation(
        Long id,
        String name,
        String description,
        Integer gigabyteCount,
        Integer minutesCount,
        Integer smsCount,
        Double price
) {
    public static TariffPresentation create(Tariff tariff) {
        return new TariffPresentation(
                tariff.getId(),
                tariff.getName(),
                tariff.getDescription(),
                tariff.getGigabyteCount(),
                tariff.getMinutesCount(),
                tariff.getSmsCount(),
                tariff.getPrice()
        );
    }
}
