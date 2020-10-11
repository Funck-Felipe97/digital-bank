package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TokenAcessoGenerator;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import com.funck.digitalbank.infrastructure.util.GeradorDigito;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@Service
public class TokenAcessoGeneratorDefault implements TokenAcessoGenerator {

    private final TokenAcessoRepository tokenAcessoRepository;
    private final Long tempoDuracaoToken;

    @Autowired
    public TokenAcessoGeneratorDefault(
            TokenAcessoRepository tokenAcessoRepository,
            EmailSender emailSender,
            @Value("${token.tempo-validade:300}") Long tempoDuracaoToken) {
        this.tokenAcessoRepository = tokenAcessoRepository;
        this.tempoDuracaoToken = tempoDuracaoToken;
    }

    @Override
    public TokenAcesso criarToken(@NotNull final Conta conta) {
        log.info("Criando novo token: " + conta);

        var dataValidade = LocalDateTime.now().plusSeconds(tempoDuracaoToken);

        var token = TokenAcesso.builder()
                .token(GeradorDigito.gerar(6))
                .conta(conta)
                .dataValidade(dataValidade)
                .usado(false)
                .build();

        return tokenAcessoRepository.save(token);
    }

}
