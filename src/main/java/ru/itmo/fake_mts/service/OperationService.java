package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.fake_mts.dto.OperationPresentation;
import ru.itmo.fake_mts.entity.OperationType;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.exception.UserNotFoundException;
import ru.itmo.fake_mts.repo.OperationRepository;
import ru.itmo.fake_mts.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;

    private final UserRepository userRepository;

    public List<OperationPresentation> getOperationsByUserAndPeriod(Long userId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return operationRepository.getOperationsByUserAndTimeBetween(user, periodStart, periodEnd).stream()
                .map(OperationPresentation::create).toList();
    }

    public List<OperationPresentation> getOperationsByUserAndPeriodAndType(
            Long userId,
            LocalDateTime periodStart, LocalDateTime periodEnd,
            OperationType operationType
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return operationRepository.getOperationsByUserAndTimeBetweenAndOperationType(
                user,
                periodStart, periodEnd,
                operationType
        ).stream().map(OperationPresentation::create).toList();
    }

    public List<OperationPresentation> getIncomeOperationsByUserAndPeriod(
            Long userId,
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                userId,
                periodStart, periodEnd,
                OperationType.INCOME
        );
    }


    public List<OperationPresentation> getOutcomeOperationsByUserAndPeriod(
            Long userId,
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                userId,
                periodStart, periodEnd,
                OperationType.OUTCOME
        );
    }

    public List<OperationPresentation> getCashbackOperationsByUserAndPeriod(
            Long userId,
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                userId,
                periodStart, periodEnd,
                OperationType.CASHBACK
        );
    }
}
