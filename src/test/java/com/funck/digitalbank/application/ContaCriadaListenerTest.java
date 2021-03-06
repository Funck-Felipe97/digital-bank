package com.funck.digitalbank.application;

import com.funck.digitalbank.application.events.ContaCriadaEvent;
import com.funck.digitalbank.application.impl.ContaCriadaListener;
import com.funck.digitalbank.application.impl.CadastroContaDefault;
import com.funck.digitalbank.infrastructure.config.BancoConfig;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class ContaCriadaListenerTest {

    @InjectMocks
    private ContaCriadaListener contaCriadaListener;

    @Mock
    private EmailSender emailSender;

    @Spy
    private BancoConfig bancoConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve enviar o email para o dono da conta informando que a conta foi criada")
    public void testProcederContaCriada() {
        // given
        var pessoa = Pessoa.builder().email("fake@gmail.com").build();
        var proposta = PropostaConta.builder().pessoa(pessoa).build();
        var conta = Conta.builder().proposta(proposta).build();
        var contaCriadaEvent = new ContaCriadaEvent(CadastroContaDefault.class, conta);

        // when
        contaCriadaListener.procederContaCriada(contaCriadaEvent);

        // then
        var captor = ArgumentCaptor.forClass(Email.class);

        verify(emailSender).send(captor.capture());

        var email = captor.getValue();

        assertAll("email", () -> {
            assertEquals("Sua conta foi criada com sucesso!", email.getTitulo());
            assertEquals(conta.info(), email.getMensagem());
            assertEquals("fake@gmail.com", email.getDestinatario());
        });
    }
}
