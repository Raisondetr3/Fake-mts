package ru.itmo.fake_mts.admin_status;

import org.springframework.stereotype.Component;

@Component("PENDING")
public class PendingStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "Your application for an administrator is pending.";
    }
}
