package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.ContaCriada;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
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

    @Override
    public void procederContaCriada(final Conta conta) {
        var email = Email.builder()
                .titulo("Sua conta foi criada com sucesso!")
                .mensagem(conta.toString())
                .destinatario(conta.getProposta().getPessoa().getEmail())
                .emitente(emailEmpresa)
                .build();

        emailSender.send(email);
    }

}
