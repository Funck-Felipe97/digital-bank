package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.ContaCriada;
import com.funck.digitalbank.application.events.ContaCriadaEvent;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j
@EnableAsync
@Component
public class ContaCriadaListener implements ContaCriada {

    private final String emailEmpresa;
    private final EmailSender emailSender;

    public ContaCriadaListener(
            @Value("${informacoes-empresa.email:null}") String emailEmpresa,
            EmailSender emailSender) {
        this.emailEmpresa = emailEmpresa;
        this.emailSender = emailSender;
    }

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
                .emitente(emailEmpresa)
                .build();

        emailSender.send(email);
    }

}
