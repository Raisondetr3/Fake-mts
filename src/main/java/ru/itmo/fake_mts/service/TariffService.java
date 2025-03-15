package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.fake_mts.dto.TariffPresentation;
import ru.itmo.fake_mts.entity.Tariff;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.exception.NotEnoughMoneyException;
import ru.itmo.fake_mts.exception.TariffNotFoundException;
import ru.itmo.fake_mts.repo.TariffRepository;
import ru.itmo.fake_mts.repo.UserRepository;

import java.util.List;
import java.util.Objects;

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

    public List<TariffPresentation> getByConditions(Integer gigabyteCount, Integer minutesCount, Integer smsCount) {
        return tariffRepository.findAll().stream().filter(
                tariff ->
                        (gigabyteCount == null || Objects.equals(tariff.getGigabyteCount(), gigabyteCount)) &&
                        (minutesCount == null || Objects.equals(tariff.getGigabyteCount(), minutesCount)) &&
                        (smsCount == null || Objects.equals(tariff.getGigabyteCount(), smsCount))
                )
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
