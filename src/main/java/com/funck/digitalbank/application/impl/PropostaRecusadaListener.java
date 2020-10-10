package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.FinalizarPropostaConta;
import com.funck.digitalbank.application.events.PropostaRecusadaEvent;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaRecusadaListener implements FinalizarPropostaConta<PropostaRecusadaEvent> {

    private final String emailEmpresa;
    private final EmailSender emailSender;

    public PropostaRecusadaListener(@Value("${informacoes-empresa.email:null}") String emailEmpresa, EmailSender emailSender) {
        this.emailEmpresa = emailEmpresa;
        this.emailSender = emailSender;
    }

    @EventListener
    @Override
    public void procederProposta(final PropostaRecusadaEvent propostaRecusadaEvent) {
        var propostaConta = propostaRecusadaEvent.getPropostaConta();

        Email email = Email.builder()
                .destinatario(propostaConta.getPessoa().getEmail())
                .emitente(emailEmpresa)
                .titulo("Você recusou nossa porposta de abertura de conta")
                .mensagem("Por favor, aceita nossa proposta para abrir um conta digital : ) ")
                .build();

        emailSender.send(email);
    }
}
