package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.fake_mts.dto.OperationPresentation;
import ru.itmo.fake_mts.entity.OperationType;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.repo.OperationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;

    private final CurrentUserService currentUserService;

    public List<OperationPresentation> getOperationsByUserAndPeriod(LocalDateTime periodStart, LocalDateTime periodEnd) {
        User user = currentUserService.getCurrentUserOrThrow();

        return operationRepository.getOperationsByUserAndTimeBetween(user, periodStart, periodEnd).stream()
                .map(OperationPresentation::create).toList();
    }

    public List<OperationPresentation> getOperationsByUserAndPeriodAndType(
            LocalDateTime periodStart, LocalDateTime periodEnd,
            OperationType operationType
    ) {
        User user = currentUserService.getCurrentUserOrThrow();

        return operationRepository.getOperationsByUserAndTimeBetweenAndOperationType(
                user,
                periodStart, periodEnd,
                operationType
        ).stream().map(OperationPresentation::create).toList();
    }

    public List<OperationPresentation> getIncomeOperationsByUserAndPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                periodStart, periodEnd,
                OperationType.INCOME
        );
    }


    public List<OperationPresentation> getOutcomeOperationsByUserAndPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                periodStart, periodEnd,
                OperationType.OUTCOME
        );
    }

    public List<OperationPresentation> getCashbackOperationsByUserAndPeriod(
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                periodStart, periodEnd,
                OperationType.CASHBACK
        );
    }
}
