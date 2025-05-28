package ru.itmo.node_a_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.common.dto.TariffPresentation;
import ru.itmo.common.entity.Tariff;
import ru.itmo.common.entity.User;
import ru.itmo.common.exception.NotEnoughMoneyException;
import ru.itmo.common.exception.TariffAlreadyActiveException;
import ru.itmo.common.exception.TariffNotFoundException;
import ru.itmo.common.repo.TariffRepository;
import ru.itmo.common.repo.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    private TariffService tariffService;

    @Lazy
    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

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

    public Map<String, String> activateTariff(Long tariffId) {
        User user = currentUserService.getCurrentUserOrThrow();
        return tariffService.activateTariff(user, tariffId);
    }

    public Map<String, String> activateTariff(Long userId, Long tariffId) {
        User user = userRepository.getById(userId);
        return tariffService.activateTariff(user, tariffId);
    }

    @Transactional
    public Map<String, String> activateTariff(User user, Long tariffId) {
        Tariff tariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found: " + tariffId));

        if (tariffId.equals(user.getTariff().getId())) {
            throw new TariffAlreadyActiveException(
                    "Tariff is already active: id=" + tariffId
            );
        }

        BigDecimal balance = user.getBalance();
        BigDecimal price   = tariff.getPrice();

        if (balance.compareTo(price) < 0) {
            throw new NotEnoughMoneyException(
                    "Not enough money, balance = " + balance
            );
        }

        user.setBalance(balance.subtract(price));
        user.setTariff(tariff);
        userRepository.save(user);

        return Map.of(
                "message",
                "OK: tariff activated; new balance = " + user.getBalance()
        );
    }

    @Transactional
    public void chargeMonthly(Long userId, Long tariffId) {
        User user   = userRepository.findById(userId)
                .orElseThrow(() -> new TariffNotFoundException("User not found: " + userId));
        Tariff tariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found: " + tariffId));

        BigDecimal balance = user.getBalance();
        BigDecimal price   = tariff.getPrice();

        if (balance.compareTo(price) < 0) {
            throw new NotEnoughMoneyException(
                    "Not enough money for monthly charge, balance = " + balance
            );
        }

        user.setBalance(balance.subtract(price));
        userRepository.save(user);
    }
}
