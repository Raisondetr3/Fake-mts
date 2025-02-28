package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.fake_mts.entity.Operation;
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

    public List<Operation> getOperationsByUserAndPeriod(Long userId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return operationRepository.getOperationsByUserAndTimeBetween(user, periodStart, periodEnd);
    }

    public List<Operation> getOperationsByUserAndPeriodAndType(
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
        );
    }

    public List<Operation> getIncomeOperationsByUserAndPeriod(
            Long userId,
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                userId,
                periodStart, periodEnd,
                OperationType.INCOME
        );
    }


    public List<Operation> getOutcomeOperationsByUserAndPeriod(
            Long userId,
            LocalDateTime periodStart, LocalDateTime periodEnd
    ) {
        return getOperationsByUserAndPeriodAndType(
                userId,
                periodStart, periodEnd,
                OperationType.OUTCOME
        );
    }

    public List<Operation> getCashbackOperationsByUserAndPeriod(
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
