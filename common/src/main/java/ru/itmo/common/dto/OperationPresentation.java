package ru.itmo.common.dto;

import ru.itmo.common.entity.Operation;
import ru.itmo.common.entity.enums.OperationType;

import java.time.LocalDateTime;

public record OperationPresentation(
        Long id,
        String name,
        String description,
        OperationType operationType,
        LocalDateTime time,
        Double price,
        UserPresentation user
) {
    public static OperationPresentation create(Operation operation) {
        return new OperationPresentation(
                operation.getId(),
                operation.getName(),
                operation.getDescription(),
                operation.getOperationType(),
                operation.getTime(),
                operation.getPrice(),
                new UserPresentation(
                        operation.getUser().getId(),
                        operation.getUser().getPhoneNumber(),
                        operation.getUser().getFullName()
                )
        );
    }
}
