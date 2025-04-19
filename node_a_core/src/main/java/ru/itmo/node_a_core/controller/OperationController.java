package ru.itmo.node_a_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.common.dto.OperationPresentation;
import ru.itmo.node_a_core.service.OperationService;

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
        return operationService.getMyOperationsByPeriod(periodStart, periodEnd);
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

    // Эндпоинты для ADMIN для получения операций конкретного пользователя
    @GetMapping("/{userId}/all-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getUserOperationsByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getUserOperationsByPeriod(userId, periodStart, periodEnd);
    }

    @GetMapping("/{userId}/income-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getUserIncomeOperationsByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getUserIncomeOperationsByPeriod(userId, periodStart, periodEnd);
    }

    @GetMapping("/{userId}/outcome-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getUserOutcomeOperationsByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getUserOutcomeOperationsByPeriod(userId, periodStart, periodEnd);
    }

    @GetMapping("/{userId}/cashback-by-period")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OperationPresentation> getUserCashbackOperationsByPeriod(
            @PathVariable Long userId,
            @RequestParam LocalDateTime periodStart,
            @RequestParam LocalDateTime periodEnd) {
        return operationService.getUserCashbackOperationsByPeriod(userId, periodStart, periodEnd);
    }
}