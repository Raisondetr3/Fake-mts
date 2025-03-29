package ru.itmo.fake_mts.admin_status;

import org.springframework.stereotype.Component;

@Component("ACCEPTED")
public class AcceptedStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "Your application has been approved. You are now an administrator.";
    }
}
