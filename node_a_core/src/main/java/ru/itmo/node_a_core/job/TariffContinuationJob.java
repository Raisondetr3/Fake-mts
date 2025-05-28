package ru.itmo.node_a_core.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import ru.itmo.common.entity.Tariff;
import ru.itmo.common.entity.User;
import ru.itmo.common.repo.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TariffContinuationJob implements Job {

    private final UserRepository userRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            Tariff tariff = user.getTariff();
            if (tariff == null) {
                continue;
            }

            BigDecimal balance = user.getBalance();
            BigDecimal price = tariff.getPrice();

            if (balance.compareTo(price) < 0) {
                user.setTariff(null);
            } else {
                user.setBalance(balance.subtract(price));
            }

            userRepository.save(user);
        }
    }
}