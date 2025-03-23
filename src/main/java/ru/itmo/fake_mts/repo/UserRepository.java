package ru.itmo.fake_mts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.entity.enums.AdminRequestStatus;
import ru.itmo.fake_mts.entity.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByRolesContaining(Role role);
    List<User> findAllByAdminRequestStatus(AdminRequestStatus status);
}

