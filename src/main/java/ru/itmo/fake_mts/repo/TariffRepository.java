package ru.itmo.fake_mts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.fake_mts.entity.Tariff;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

}
