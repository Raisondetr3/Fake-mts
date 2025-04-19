package ru.itmo.node_b_worker.eis;

public interface EmailConnectionFactory {
    EmailConnection getConnection() throws Exception;
}
