package ru.itmo.node_a_core.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.itmo.common.entity.User;
import ru.itmo.common.repo.UserRepository;

import java.util.List;

@RequiredArgsConstructor
public class TariffContinuationJob implements Job {
    private final UserRepository userRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getTariff() != null) {
                if (user.getBalance() - user.getTariff().getPrice() < 0) {
                    user.setTariff(null);  // или уходить в минус
                }
                else {
                    user.setBalance(user.getBalance() - user.getTariff().getPrice());
                }
            }
            userRepository.save(user);
        }
    }
}
