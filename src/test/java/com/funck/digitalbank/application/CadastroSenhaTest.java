package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.CadastroSenhaDefault;
import com.funck.digitalbank.domain.exceptions.BadRequestException;
import com.funck.digitalbank.domain.exceptions.TokenInvalidoException;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class CadastroSenhaTest {


    @InjectMocks
    private CadastroSenhaDefault cadastroSenha;

    @Mock
    private TokenAcessoRepository tokenAcessoRepository;

    @Mock
    private ContaRepository contaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve lançar BadRequestException quando a conta não for encontrada")
    public void testCriarSenha() {
        assertThrows(BadRequestException.class, () -> cadastroSenha.criarSenha("contaId", "12345678"));
    }

    @Test
    @DisplayName("Deve lançar BadRequestException quando nenhum token válido for encontrado")
    public void testCriarSenha2() {
        // given
        var conta = new Conta();
        conta.setSenha("contaId");

        doReturn(Optional.of(conta)).when(contaRepository).findById("contaId");

        // when then
        assertThrows(BadRequestException.class, () -> cadastroSenha.criarSenha("contaId", "12345678"));
    }

    @Test
    @DisplayName("Deve lançar TokenInvalidoException quando o token usado para definir senha não estiver validado")
    public void testCriarSenha3() {
        // given
        var conta = new Conta();
        conta.setSenha("contaId");

        var token = new TokenAcesso();
        token.setValidado(false);

        doReturn(Optional.of(conta)).when(contaRepository).findById("contaId");
        doReturn(Optional.of(token)).when(tokenAcessoRepository).findTokenValidoByConta(conta);

        // when then
        assertThrows(TokenInvalidoException.class, () -> cadastroSenha.criarSenha("contaId", "12345678"));
    }

    @Test
    @DisplayName("Deve lançar TokenInvalidoException quando o token usado para definir senha já estiver sido usado antes")
    public void testCriarSenha4() {
        // given
        var conta = new Conta();
        conta.setSenha("contaId");

        var token = new TokenAcesso();
        token.setValidado(true);
        token.setUsado(true);

        doReturn(Optional.of(conta)).when(contaRepository).findById("contaId");
        doReturn(Optional.of(token)).when(tokenAcessoRepository).findTokenValidoByConta(conta);

        // when then
        assertThrows(TokenInvalidoException.class, () -> cadastroSenha.criarSenha("contaId", "12345678"));
    }

    @Test
    @DisplayName("Deve salvar a senha encriptografada quando todas as informações estiverem corretas")
    public void testCriarSenha5() {
        // given
        var conta = new Conta();
        conta.setSenha("contaId");

        var token = new TokenAcesso();
        token.setValidado(true);
        token.setUsado(false);
        token.setDataValidade(LocalDateTime.MAX);

        doReturn(Optional.of(conta)).when(contaRepository).findById("contaId");
        doReturn(Optional.of(token)).when(tokenAcessoRepository).findTokenValidoByConta(conta);

        // when
        cadastroSenha.criarSenha("contaId", "12345678");

        // then
        var contaCaptor = ArgumentCaptor.forClass(Conta.class);

        verify(contaRepository).save(contaCaptor.capture());

        var contaSalva = contaCaptor.getValue();

        assertEquals(DigestUtils.md5DigestAsHex("12345678".getBytes()), contaSalva.getSenha());
    }

    @Test
    @DisplayName("Deve atualizar o token para utilizado quando a senha for atualizada com sucesso")
    public void testCriarSenha6() {
        // given
        var conta = new Conta();
        conta.setSenha("contaId");

        var token = new TokenAcesso();
        token.setValidado(true);
        token.setUsado(false);
        token.setDataValidade(LocalDateTime.MAX);

        doReturn(Optional.of(conta)).when(contaRepository).findById("contaId");
        doReturn(Optional.of(token)).when(tokenAcessoRepository).findTokenValidoByConta(conta);

        // when
        cadastroSenha.criarSenha("contaId", "12345678");

        // then
        var tokenCaptor = ArgumentCaptor.forClass(TokenAcesso.class);

        verify(tokenAcessoRepository).save(tokenCaptor.capture());

        var tokenSalvo = tokenCaptor.getValue();

        assertTrue(tokenSalvo.getUsado());
    }

    @Test
    @DisplayName("Deve lançar TokenInvalidoException quando o token for expirado")
    public void testCriarSenha7() {
        // given
        var conta = new Conta();
        conta.setSenha("contaId");

        var token = new TokenAcesso();
        token.setValidado(true);
        token.setUsado(false);
        token.setDataValidade(LocalDateTime.MIN);

        doReturn(Optional.of(conta)).when(contaRepository).findById("contaId");
        doReturn(Optional.of(token)).when(tokenAcessoRepository).findTokenValidoByConta(conta);

        // when then
        assertThrows(TokenInvalidoException.class, () -> cadastroSenha.criarSenha("contaId", "12345678"));
    }

}
