package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.fake_mts.entity.Tariff;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.exception.TariffNotFoundException;
import ru.itmo.fake_mts.exception.UserNotFoundException;
import ru.itmo.fake_mts.repo.TariffRepository;
import ru.itmo.fake_mts.repo.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;

    private final UserRepository userRepository;

    public List<Tariff> getAllTariffs() {
        return tariffRepository.findAll();
    }

    public Tariff getById(Long tariffId) {
        return tariffRepository.findById(tariffId).orElseThrow(() -> new TariffNotFoundException("Tariff not found"));
    }

    public Boolean activateTariff(Long userId, Long tariffId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Tariff tariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new RuntimeException("Tariff not found"));

        if (user.getBalance() < tariff.getPrice()) {
            return false;
        }

        user.setBalance(user.getBalance() - tariff.getPrice());
        user.setTariff(tariff);

        userRepository.save(user);

        return true;
    }
}
