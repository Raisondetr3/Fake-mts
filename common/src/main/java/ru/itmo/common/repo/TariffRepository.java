package ru.itmo.common.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.common.entity.Tariff;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

}
