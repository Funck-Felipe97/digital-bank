package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.AcessoContaDefault;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class AcessoContaTest {

    @InjectMocks
    private AcessoContaDefault acessoConta;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TokenAcessoGenerator tokenAcessoGenerator;

    @Mock
    private EmailSender emailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException quando a conta não for encontrada")
    public void testPrimeiroAcesso() {
        assertThrows(NoSuchElementException.class, () -> acessoConta.primeiroAcesso("fake@gmail", "000.000.000.00"));
    }

    @Test
    @DisplayName("Deve enviar um email com o token quando as informações estiverem corretas")
    public void testPrimeiroAcesso2() {
        // given
        var pessoa = Pessoa.builder().email("fake@gmail.com").build();
        var proposta = PropostaConta.builder().pessoa(pessoa).build();
        var conta = Conta.builder().proposta(proposta).build();
        var token = TokenAcesso.builder().token("123456").build();

        doReturn(Optional.of(conta)).when(contaRepository).findByEmailAndCpfPessoa("fake@gmail", "000.000.000.00");
        doReturn(token).when(tokenAcessoGenerator).criarToken(conta);

        // when
        acessoConta.primeiroAcesso("fake@gmail", "000.000.000.00");

        // then
        var captor = ArgumentCaptor.forClass(Email.class);

        verify(emailSender).send(captor.capture());
        var email = captor.getValue();
        assertAll("email", () -> {
            assertEquals("utilize o token para logar no aplicativo", email.getTitulo());
            assertEquals("Token: 123456", email.getMensagem());
            assertEquals("fake@gmail", email.getDestinatario());
        });
    }

}
