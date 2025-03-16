package ru.itmo.fake_mts.dto;

import ru.itmo.fake_mts.entity.Operation;
import ru.itmo.fake_mts.entity.enums.OperationType;

import java.time.LocalDateTime;

public record OperationPresentation(
        Long id,
        String name,
        String description,
        OperationType operationType,
        LocalDateTime time,
        Double price
) {
    public static OperationPresentation create(Operation operation) {
        return new OperationPresentation(
                operation.getId(),
                operation.getName(),
                operation.getDescription(),
                operation.getOperationType(),
                operation.getTime(),
                operation.getPrice()
        );
    }
}
