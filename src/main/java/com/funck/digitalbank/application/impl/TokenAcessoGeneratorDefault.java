package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TokenAcessoGenerator;
import com.funck.digitalbank.infrastructure.config.BancoConfig;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import com.funck.digitalbank.infrastructure.util.GeradorDigito;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenAcessoGeneratorDefault implements TokenAcessoGenerator {

    private final TokenAcessoRepository tokenAcessoRepository;
    private final BancoConfig bancoConfig;

    @Override
    public TokenAcesso criarToken(@NotNull final Conta conta) {
        log.info("Criando novo token: " + conta);

        var dataValidade = LocalDateTime.now().plusSeconds(bancoConfig.getTokenExpiration());

        var token = TokenAcesso.builder()
                .token(GeradorDigito.gerar(6))
                .conta(conta)
                .dataValidade(dataValidade)
                .usado(false)
                .validado(false)
                .build();

        return tokenAcessoRepository.save(token);
    }

}
