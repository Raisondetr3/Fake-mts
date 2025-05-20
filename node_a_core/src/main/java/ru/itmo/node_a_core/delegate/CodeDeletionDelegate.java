package ru.itmo.node_a_core.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.CodeStorage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("codeDeletionDelegate")
@RequiredArgsConstructor
public class CodeDeletionDelegate implements JavaDelegate {

    private final CodeStorage codeStorage;

    @Override
    public void execute(DelegateExecution execution) {
        Instant now = Instant.now();
        List<String> toRemove = new ArrayList<>();

        Map<String, Pair<String, Instant>> phoneToCode = codeStorage.getPhoneToCode();

        phoneToCode.forEach((phone, codePair) -> {
            Instant createdAt = codePair.getSecond();
            if (createdAt.plus(1, ChronoUnit.MINUTES).isBefore(now)) {
                toRemove.add(phone);
            }
        });

        toRemove.forEach(codeStorage::removeCodeForPhone);
    }
}
