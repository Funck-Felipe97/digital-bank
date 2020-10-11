package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.TokenAcessoGeneratorDefault;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TokenAcessoGeneratorTest {

    private TokenAcessoGenerator tokenAcessoGenerator;
    private TokenAcessoRepository tokenAcessoRepository;
    private Long tempoDuracaoToken;

    @BeforeEach
    public void setUp() {
        tokenAcessoRepository = mock(TokenAcessoRepository.class);
        tempoDuracaoToken = 300L;
        tokenAcessoGenerator = new TokenAcessoGeneratorDefault(tokenAcessoRepository, tempoDuracaoToken);
    }

    @Test
    public void testCriarToken() {
        // given
        var conta = new Conta();
        conta.setId("contaId");

        // when
        tokenAcessoGenerator.criarToken(conta);

        // then
        var tokenCaptor = ArgumentCaptor.forClass(TokenAcesso.class);

        verify(tokenAcessoRepository).save(tokenCaptor.capture());

        var token = tokenCaptor.getValue();

        assertAll("token", () -> {
            assertFalse(token.getUsado());
            assertEquals(conta, token.getConta());
            assertEquals(6, token.getToken().length());
        });
    }
}
