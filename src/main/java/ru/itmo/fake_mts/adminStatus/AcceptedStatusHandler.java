package ru.itmo.fake_mts.adminStatus;

import org.springframework.stereotype.Component;

@Component("ACCEPTED")
public class AcceptedStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "Your application has been approved. You are now an administrator.";
    }
}
