package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TokenValidator;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenValidadorDefault implements TokenValidator {

    private final TokenAcessoRepository tokenAcessoRepository;

    @Override
    public void validar(@NotNull final String contaId, @NotNull final String token) {
        var tokenAcesso = tokenAcessoRepository.findByTokenAndConta_id(token, contaId)
                .orElseThrow(() -> new NoSuchElementException("Token inválido : ("));

        if (Boolean.TRUE.equals(tokenAcesso.getUsado())) {
            throw new IllegalArgumentException("Este token já foi utilizado, sorry : (");
        }

        if (LocalDateTime.now().isAfter(tokenAcesso.getDataValidade())) {
            throw new IllegalArgumentException("Este token experiou , sorry : (");
        }

        tokenAcesso.setUsado(true);

        tokenAcessoRepository.save(tokenAcesso);
    }

}
