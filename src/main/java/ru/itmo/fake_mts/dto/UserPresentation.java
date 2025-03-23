package ru.itmo.fake_mts.dto;

public record UserPresentation(
        Long id,
        String phoneNumber,
        String fullName
) { }
