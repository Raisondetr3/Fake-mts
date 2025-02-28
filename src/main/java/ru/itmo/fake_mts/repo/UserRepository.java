package ru.itmo.fake_mts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.fake_mts.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}

