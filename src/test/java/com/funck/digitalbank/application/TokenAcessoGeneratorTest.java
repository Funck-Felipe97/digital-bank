package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.TokenAcessoGeneratorDefault;
import com.funck.digitalbank.infrastructure.config.BancoConfig;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;

public class TokenAcessoGeneratorTest {

    @InjectMocks
    private TokenAcessoGeneratorDefault tokenAcessoGenerator;

    @Mock
    private TokenAcessoRepository tokenAcessoRepository;

    @Spy
    private BancoConfig bancoConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
