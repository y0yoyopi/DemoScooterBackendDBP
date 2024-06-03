package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.events;

import org.springframework.context.ApplicationEvent;

public class HelloEmailEvent extends ApplicationEvent {
    private final String email;

    public HelloEmailEvent(Object source, String email) {
        super(source);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
