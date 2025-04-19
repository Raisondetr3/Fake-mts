package ru.itmo.node_b_worker.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String email, String message) {
        System.out.println("Send on email: " + email + " with message: " + message);
    }
}
