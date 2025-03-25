package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.fake_mts.dto.OperationPresentation;
import ru.itmo.fake_mts.entity.Operation;
import ru.itmo.fake_mts.entity.enums.OperationType;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.repo.OperationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperationService {
    private final OperationRepository operationRepository;
    private final CurrentUserService currentUserService;

    public List<OperationPresentation> getMyOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        User currentUser = currentUserService.getCurrentUserOrThrow();
        List<Operation> operations = operationRepository.getOperationsByUserAndTimeBetween(
                currentUser, periodStart, periodEnd);
        return operations.stream()
                .map(OperationPresentation::create)
                .toList();
    }

    public List<OperationPresentation> getOperationsByUserAndPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        return getMyOperationsByPeriod(periodStart, periodEnd);
    }

    public List<OperationPresentation> getAllOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        List<Operation> operations = operationRepository.getOperationsByTimeBetween(periodStart, periodEnd);
        return operations.stream()
                .map(OperationPresentation::create)
                .toList();
    }

    public List<OperationPresentation> getMyOperationsByPeriodAndType(
            LocalDateTime periodStart, LocalDateTime periodEnd, OperationType operationType) {
        User currentUser = currentUserService.getCurrentUserOrThrow();
        List<Operation> operations = operationRepository.getOperationsByUserAndTimeBetweenAndOperationType(
                currentUser, periodStart, periodEnd, operationType);
        return operations.stream()
                .map(OperationPresentation::create)
                .toList();
    }

    public List<OperationPresentation> getAllOperationsByPeriodAndType(
            LocalDateTime periodStart, LocalDateTime periodEnd, OperationType operationType) {
        List<Operation> operations = operationRepository.getOperationsByTimeBetweenAndOperationType(
                periodStart, periodEnd, operationType);
        return operations.stream()
                .map(OperationPresentation::create)
                .toList();
    }

    public List<OperationPresentation> getMyIncomeOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        return getMyOperationsByPeriodAndType(periodStart, periodEnd, OperationType.INCOME);
    }

    public List<OperationPresentation> getAllIncomeOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        return getAllOperationsByPeriodAndType(periodStart, periodEnd, OperationType.INCOME);
    }

    public List<OperationPresentation> getMyOutcomeOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        return getMyOperationsByPeriodAndType(periodStart, periodEnd, OperationType.OUTCOME);
    }

    public List<OperationPresentation> getAllOutcomeOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        return getAllOperationsByPeriodAndType(periodStart, periodEnd, OperationType.OUTCOME);
    }

    public List<OperationPresentation> getMyCashbackOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        return getMyOperationsByPeriodAndType(periodStart, periodEnd, OperationType.CASHBACK);
    }

    public List<OperationPresentation> getAllCashbackOperationsByPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd) {
        return getAllOperationsByPeriodAndType(periodStart, periodEnd, OperationType.CASHBACK);
    }
}