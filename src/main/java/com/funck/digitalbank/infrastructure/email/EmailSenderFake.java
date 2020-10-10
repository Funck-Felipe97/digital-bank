package com.funck.digitalbank.infrastructure.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("dev")
@Component
public class EmailSenderFake implements EmailSender {

    @Override
    public void send(final Email email) {
        log.info("enviando e-mail fake:" + email);
    }

}
