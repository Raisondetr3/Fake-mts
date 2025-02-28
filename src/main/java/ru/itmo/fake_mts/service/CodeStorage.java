package ru.itmo.fake_mts.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CodeStorage {

    private final Map<String, String> phoneToCode = new HashMap<>();

    public void saveCodeForPhone(String phone, String code) {
        phoneToCode.put(phone, code);
    }

    public String getCodeForPhone(String phone) {
        return phoneToCode.get(phone);
    }

    public void removeCodeForPhone(String phone) {
        phoneToCode.remove(phone);
    }
}

