package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.dto.OperationPresentation;
import ru.itmo.fake_mts.service.OperationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @GetMapping("/all-by-period")
    public List<OperationPresentation> getAllByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getOperationsByUserAndPeriod(
                periodStart,
                periodEnd
        );
    }

    @GetMapping("/income-by-period")
    public List<OperationPresentation> getIncomeByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getIncomeOperationsByUserAndPeriod(
                periodStart,
                periodEnd
        );
    }

    @GetMapping("/outcome-by-period")
    public List<OperationPresentation> getOutcomeByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getOutcomeOperationsByUserAndPeriod(
                periodStart,
                periodEnd
        );
    }

    @GetMapping("/cashback-by-period")
    public List<OperationPresentation> getCashbackByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd
    ) {
        return operationService.getCashbackOperationsByUserAndPeriod(
                periodStart,
                periodEnd
        );
    }
}

