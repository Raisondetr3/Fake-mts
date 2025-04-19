package ru.itmo.node_b_worker.eis;

public interface EmailConnection extends AutoCloseable {
    void sendMail(String to, String subject, String body);

    @Override
    void close();
}
