package ru.itmo.fake_mts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {
    public void sendSms(String phoneNumber, String code) {
        log.info("Send SMS code for {}: {}", phoneNumber, code);
    }
}

