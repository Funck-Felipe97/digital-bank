package com.funck.digitalbank.application.events;

import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaRecusadaListener implements ApplicationListener<PropostaRecusadaEvent> {

    private final String emailEmpresa;
    private final EmailSender emailSender;

    public PropostaRecusadaListener(@Value("${informacoes-empresa.email}") String emailEmpresa, EmailSender emailSender) {
        this.emailEmpresa = emailEmpresa;
        this.emailSender = emailSender;
    }

    @Override
    public void onApplicationEvent(PropostaRecusadaEvent propostaRecusadaEvent) {
        var proposta = propostaRecusadaEvent.getPropostaConta();

        Email email = Email.builder()
                .destinatario(proposta.getPessoa().getEmail())
                .emitente(emailEmpresa)
                .titulo("Você recusou nossa porposta de abertura de conta")
                .mensagem("Por favor, aceita nossa proposta para abrir um conta digital : ) ")
                .build();

        emailSender.send(email);
    }

}
