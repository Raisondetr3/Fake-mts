package ru.itmo.common.admin_status;

import org.springframework.stereotype.Component;

@Component
public class PendingStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "Your application for an administrator is pending.";
    }
}
