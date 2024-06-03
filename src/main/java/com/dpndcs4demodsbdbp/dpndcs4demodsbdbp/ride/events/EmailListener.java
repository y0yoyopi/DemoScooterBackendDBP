package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.events;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @Async
    @EventListener
    public void handleHelloEmailEvent(HelloEmailEvent event) {
        emailService.sendSimpleMessage(event.getEmail(), "Correo de Prueba", "Este es un correo de prueba");
    }
}