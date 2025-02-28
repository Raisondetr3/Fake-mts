package ru.itmo.fake_mts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.fake_mts.entity.Operation;
import ru.itmo.fake_mts.entity.OperationType;
import ru.itmo.fake_mts.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationRepository  extends JpaRepository<Operation, Long> {
    List<Operation> getOperationsByUserAndTimeBetween(User user, LocalDateTime periodStart, LocalDateTime periodEnd);

    List<Operation> getOperationsByUserAndTimeBetweenAndOperationType(
            User user,
            LocalDateTime periodStart, LocalDateTime periodEnd,
            OperationType operationType
    );
}
