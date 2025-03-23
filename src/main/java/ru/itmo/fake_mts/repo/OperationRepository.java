package ru.itmo.fake_mts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.fake_mts.entity.Operation;
import ru.itmo.fake_mts.entity.enums.OperationType;
import ru.itmo.fake_mts.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationRepository  extends JpaRepository<Operation, Long> {
    List<Operation> getOperationsByUserAndTimeBetween(User user, LocalDateTime periodStart, LocalDateTime periodEnd);

    List<Operation> getOperationsByTimeBetween(LocalDateTime periodStart, LocalDateTime periodEnd);
    
    List<Operation> getOperationsByUserAndTimeBetweenAndOperationType(
            User user,
            LocalDateTime periodStart, LocalDateTime periodEnd,
            OperationType operationType
    );

    // для ADMIN
    List<Operation> getOperationsByTimeBetweenAndOperationType(
            LocalDateTime periodStart, LocalDateTime periodEnd, OperationType operationType
    );
}
