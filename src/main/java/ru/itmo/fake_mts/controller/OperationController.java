package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Для всех пользователей (обычный пользователь получает только свои операции)
    @GetMapping("/all-by-period")
    public List<OperationPresentation> getMyOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getOperationsByUserAndPeriod(periodStart, periodEnd);
    }

    @GetMapping("/income-by-period")
    public List<OperationPresentation> getMyIncomeOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getMyIncomeOperationsByPeriod(periodStart, periodEnd);
    }

    @GetMapping("/outcome-by-period")
    public List<OperationPresentation> getMyOutcomeOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getMyOutcomeOperationsByPeriod(periodStart, periodEnd);
    }

    @GetMapping("/cashback-by-period")
    public List<OperationPresentation> getMyCashbackOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getMyCashbackOperationsByPeriod(periodStart, periodEnd);
    }

    // Эндпоинты для ADMIN для получения операций всех пользователей
    @GetMapping("/admin/all-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getAllOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getAllOperationsByPeriod(periodStart, periodEnd);
    }

    @GetMapping("/admin/income-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getAllIncomeOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getAllIncomeOperationsByPeriod(periodStart, periodEnd);
    }

    @GetMapping("/admin/outcome-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getAllOutcomeOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getAllOutcomeOperationsByPeriod(periodStart, periodEnd);
    }

    @GetMapping("/admin/cashback-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getAllCashbackOperationsByPeriod(
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getAllCashbackOperationsByPeriod(periodStart, periodEnd);
    }
}