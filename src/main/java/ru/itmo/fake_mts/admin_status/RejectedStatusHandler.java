package ru.itmo.fake_mts.admin_status;

import org.springframework.stereotype.Component;

@Component("REJECTED")
public class RejectedStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "Your application has been rejected.";
    }
}

