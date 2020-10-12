package com.funck.digitalbank.infrastructure.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("prod")
@RequiredArgsConstructor
@Component
public class EmailSenderDefault implements EmailSender {

    private final JavaMailSender emailSender;

    @Async
    @Override
    public void send(final Email email) {
        log.info("Enviando e-mail real: " + email);

        var simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject(email.getTitulo());
        simpleMailMessage.setText(email.getMensagem());
        simpleMailMessage.setTo(email.getDestinatario());
        simpleMailMessage.setFrom(email.getEmitente());

        emailSender.send(simpleMailMessage);
    }

}
