package ru.itmo.common.admin_status;

import org.springframework.stereotype.Component;

@Component
public class NoneStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "You have not yet submitted an admin request.";
    }
}
