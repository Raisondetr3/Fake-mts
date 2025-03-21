package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.fake_mts.dto.TariffPresentation;
import ru.itmo.fake_mts.entity.Tariff;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.exception.NotEnoughMoneyException;
import ru.itmo.fake_mts.exception.TariffNotFoundException;
import ru.itmo.fake_mts.repo.TariffRepository;
import ru.itmo.fake_mts.repo.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;

    private final UserRepository userRepository;

    private final CurrentUserService currentUserService;

    public List<TariffPresentation> getByConditions(Integer gigabyteCount, Integer minutesCount, Integer smsCount) {
        return tariffRepository.findAll().stream().filter(
                tariff ->
                        (gigabyteCount == null || Objects.equals(tariff.getGigabyteCount(), gigabyteCount)) &&
                        (minutesCount == null || Objects.equals(tariff.getMinutesCount(), minutesCount)) &&
                        (smsCount == null || Objects.equals(tariff.getSmsCount(), smsCount))
                )
                .map(TariffPresentation::create).toList();
    }

    public TariffPresentation getById(Long tariffId) {
        return tariffRepository.findById(tariffId)
                .map(TariffPresentation::create)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found"));
    }

    @Transactional
    public Map<String, String> activateTariff(Long tariffId) {
        User user = currentUserService.getCurrentUserOrThrow();

        Tariff tariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found"));

        if (user.getBalance() < tariff.getPrice()) {
            throw new NotEnoughMoneyException("Not enough money, user balance = " + user.getBalance());
        }

        user.setBalance(user.getBalance() - tariff.getPrice());
        user.setTariff(tariff);

        userRepository.save(user);

        return Map.of("message", "OK: tariff activated. User balance = " + user.getBalance());
    }
}
