package ru.itmo.common.dto;

public record UserPresentation(
        Long id,
        String phoneNumber,
        String fullName
) { }
