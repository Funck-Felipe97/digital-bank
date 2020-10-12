package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TokenValidator;
import com.funck.digitalbank.domain.exceptions.TokenInvalidoException;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenValidatorDefault implements TokenValidator {

    private final TokenAcessoRepository tokenAcessoRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void validar(@NotNull final String contaId, @NotNull final String token) {
        var tokenAcesso = tokenAcessoRepository.findByTokenAndConta_id(token, contaId)
                .orElseThrow(() -> new TokenInvalidoException("Token inválido : ("));

        if (Boolean.TRUE.equals(tokenAcesso.getUsado())) {
            throw new TokenInvalidoException("Este token já foi utilizado, sorry : (");
        }

        if (LocalDateTime.now().isAfter(tokenAcesso.getDataValidade())) {
            throw new TokenInvalidoException("Este token experiou , sorry : (");
        }

        tokenAcesso.setValidado(true);

        tokenAcessoRepository.save(tokenAcesso);
    }

}
