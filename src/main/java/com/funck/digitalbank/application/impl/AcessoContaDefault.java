package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.AcessoConta;
import com.funck.digitalbank.application.TokenAcessoGenerator;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AcessoContaDefault implements AcessoConta {

    private final ContaRepository contaRepository;
    private final TokenAcessoGenerator tokenAcessoGenerator;
    private final EmailSender emailSender;

    @Override
    public TokenAcesso primeiroAcesso(@NotNull final String email, @NotNull final String cpf) {
        var conta = contaRepository.findByEmailAndCpfPessoa(email, cpf)
                .orElseThrow(() -> new NoSuchElementException("Não foi encontrado uma conta com o cpf e email informado"));

        var token = tokenAcessoGenerator.criarToken(conta);

        sendEmail(email, token);

        return token;
    }

    @Async
    public void sendEmail(final String emailPessoa, final TokenAcesso token) {
        log.info("Enviando email com o token: " + token);

        var email = Email.builder()
                .emitente(null)
                .destinatario(emailPessoa)
                .titulo("utilize o token para logar no aplicativo")
                .mensagem("Token: " + token.getToken())
                .build();

        emailSender.send(email);
    }

}
