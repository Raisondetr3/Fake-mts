package ru.itmo.fake_mts.adminStatus;

import org.springframework.stereotype.Component;

@Component("NONE")
public class NoneStatusHandler implements AdminRequestStatusHandler {
    @Override
    public String getStatusMessage() {
        return "You have not yet submitted an admin request.";
    }
}
