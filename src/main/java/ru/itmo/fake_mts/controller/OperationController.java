package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.entity.Operation;
import ru.itmo.fake_mts.service.OperationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @GetMapping("/{userId}/all-by-period")
    public List<Operation> getAllByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getOperationsByUserAndPeriod(
                userId,
                periodStart,
                periodEnd
        );
    }

    @GetMapping("/{userId}/income-by-period")
    public List<Operation> getIncomeByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getIncomeOperationsByUserAndPeriod(
                userId,
                periodStart,
                periodEnd
        );
    }

    @GetMapping("/{userId}/outcome-by-period")
    public List<Operation> getOutcomeByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getOutcomeOperationsByUserAndPeriod(
                userId,
                periodStart,
                periodEnd
        );
    }

    @GetMapping("/{userId}/cashback-by-period")
    public List<Operation> getCashbackByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getCashbackOperationsByUserAndPeriod(
                userId,
                periodStart,
                periodEnd
        );
    }
}

