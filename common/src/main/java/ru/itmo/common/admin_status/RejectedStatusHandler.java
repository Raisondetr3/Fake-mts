package ru.itmo.common.admin_status;

import org.springframework.stereotype.Component;

@Component
public class RejectedStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "Your application has been rejected.";
    }
}

