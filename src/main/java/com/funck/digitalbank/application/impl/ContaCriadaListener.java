package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.ContaCriada;
import com.funck.digitalbank.application.events.ContaCriadaEvent;
import com.funck.digitalbank.infrastructure.config.BancoConfig;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContaCriadaListener implements ContaCriada {

    private final BancoConfig bancoConfig;
    private final EmailSender emailSender;

    @Async
    @EventListener
    @Override
    public void procederContaCriada(final ContaCriadaEvent contaCriadaEvent) {
        log.info("Enviando email de conta criada");

        var conta = contaCriadaEvent.getConta();

        var email = Email.builder()
                .titulo("Sua conta foi criada com sucesso!")
                .mensagem(conta.toString())
                .destinatario(conta.getProposta().getPessoa().getEmail())
                .emitente(bancoConfig.getEmail())
                .build();

        emailSender.send(email);
    }

}
