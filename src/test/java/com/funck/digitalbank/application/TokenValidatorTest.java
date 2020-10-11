package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.TokenValidatorDefault;
import com.funck.digitalbank.domain.exceptions.TokenInvalidoException;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class TokenValidatorTest {

    @InjectMocks
    private TokenValidatorDefault tokenValidator;

    @Mock
    private TokenAcessoRepository tokenAcessoRepository;

    private TokenAcesso tokenAcesso;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        tokenAcesso = new TokenAcesso();
        tokenAcesso.setId("tokenId");
    }

    @Test
    @DisplayName("Deve lançar TokenInvalidoException quando o token não for encontrado")
    public void testValidar() {
        assertThrows(TokenInvalidoException.class, () -> tokenValidator.validar("idConta", "123456"));
    }

    @Test
    @DisplayName("Deve lançar TokenInvalidoException quando o token já foi utilizado antes")
    public void testValidar2() {
        // given
        tokenAcesso.setUsado(true);

        doReturn(Optional.of(tokenAcesso)).when(tokenAcessoRepository).findByTokenAndConta_id("123456", "fake@gmail.com");

        // when then
        assertThrows(TokenInvalidoException.class, () -> tokenValidator.validar("idConta", "123456"));
    }

    @Test
    @DisplayName("Deve lançar TokenInvalidoException quando o token estiver vencido")
    public void testValidar3() {
        // given
        tokenAcesso.setUsado(false);
        tokenAcesso.setDataValidade(LocalDateTime.now().minusMinutes(10));

        doReturn(Optional.of(tokenAcesso)).when(tokenAcessoRepository).findByTokenAndConta_id("123456", "idConta");

        // when then
        assertThrows(TokenInvalidoException.class, () -> tokenValidator.validar("idConta", "123456"));
    }

    @Test
    @DisplayName("Deve alterar o token para usado após a validação do token estiver ok")
    public void testValidar4() {
        // given
        tokenAcesso.setUsado(false);
        tokenAcesso.setDataValidade(LocalDateTime.now().plusMinutes(10));

        doReturn(Optional.of(tokenAcesso)).when(tokenAcessoRepository).findByTokenAndConta_id("123456", "idConta");

        // when
        tokenValidator.validar("idConta", "123456");

        // then
        var captor = ArgumentCaptor.forClass(TokenAcesso.class);

        verify(tokenAcessoRepository).save(captor.capture());

        assertTrue(captor.getValue().getUsado());
    }
}
