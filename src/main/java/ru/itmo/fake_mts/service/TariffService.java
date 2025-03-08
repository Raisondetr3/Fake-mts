package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.fake_mts.dto.TariffPresentation;
import ru.itmo.fake_mts.entity.Tariff;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.exception.NotEnoughMoneyException;
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
    private final CurrentUserService currentUserService;

    public List<TariffPresentation> getAllTariffs() {
        return tariffRepository.findAll().stream()
                .map(TariffPresentation::create).toList();
    }

    public TariffPresentation getById(Long tariffId) {
        return tariffRepository.findById(tariffId)
                .map(TariffPresentation::create)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found"));
    }

    public String activateTariff(Long tariffId) {
        User user = currentUserService.getCurrentUserOrThrow();

        Tariff tariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found"));

        if (user.getBalance() < tariff.getPrice()) {
            throw new NotEnoughMoneyException("Not enough money, user balance = " + user.getBalance());
        }

        user.setBalance(user.getBalance() - tariff.getPrice());
        user.setTariff(tariff);

        userRepository.save(user);

        return "OK: tariff activated. User balance = " + user.getBalance();
    }
}
