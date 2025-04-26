package ru.itmo.node_a_core.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.util.Pair;
import ru.itmo.node_a_core.service.CodeStorage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CodeDeletionJob implements Job {
    private final CodeStorage codeStorage;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Instant now = Instant.now();
        Map<String, Pair<String, Instant>> phoneToCode = codeStorage.getPhoneToCode();
        List<String> phoneToDeleteCode = new ArrayList<>();
        for (String phone : phoneToCode.keySet()) {
            Instant instant = phoneToCode.get(phone).getSecond();
            // время жизни кода - минута
            if (instant.plus(1, ChronoUnit.MINUTES).isAfter(now)) {
                phoneToDeleteCode.add(phone);
            }
        }
        phoneToDeleteCode.forEach(codeStorage::removeCodeForPhone);
    }
}
