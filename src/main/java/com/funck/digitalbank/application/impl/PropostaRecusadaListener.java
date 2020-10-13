package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.FinalizarPropostaConta;
import com.funck.digitalbank.application.events.PropostaRecusadaEvent;
import com.funck.digitalbank.infrastructure.config.BancoConfig;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PropostaRecusadaListener implements FinalizarPropostaConta<PropostaRecusadaEvent> {

    private final BancoConfig bancoConfig;
    private final EmailSender emailSender;

    @EventListener
    @Override
    public void procederProposta(final PropostaRecusadaEvent propostaRecusadaEvent) {
        var propostaConta = propostaRecusadaEvent.getPropostaConta();

        Email email = Email.builder()
                .destinatario(propostaConta.getPessoa().getEmail())
                .emitente(bancoConfig.getEmail())
                .titulo("Você recusou nossa proposta de abertura de conta")
                .mensagem("Por favor, aceita nossa proposta para abrir um conta digital : ) ")
                .build();

        emailSender.send(email);
    }
}
