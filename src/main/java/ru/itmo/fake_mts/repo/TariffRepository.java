package ru.itmo.fake_mts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.fake_mts.entity.Tariff;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

}
