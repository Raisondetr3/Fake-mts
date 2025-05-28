package ru.itmo.node_a_core.service;

import lombok.Getter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Service
public class CodeStorage {
    private final Map<String, Pair<String, Instant>> phoneToCode = new HashMap<>();

    public void saveCodeForPhone(String phone, String code) {
        phoneToCode.put(phone, Pair.of(code, Instant.now()));
    }

    public String getCodeForPhone(String phone) {
        if (phoneToCode.get(phone) == null) return null;
        return phoneToCode.get(phone).getFirst();
    }

    public void removeCodeForPhone(String phone) {
        phoneToCode.remove(phone);
    }

    public Map<String, String> getAllCodes() {
        return phoneToCode.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getFirst()
                ));
    }
}

