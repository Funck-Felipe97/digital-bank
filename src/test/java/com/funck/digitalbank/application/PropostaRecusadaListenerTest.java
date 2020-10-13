package com.funck.digitalbank.application;

import com.funck.digitalbank.application.events.PropostaRecusadaEvent;
import com.funck.digitalbank.application.impl.PropostaRecusadaListener;
import com.funck.digitalbank.config.BancoConfig;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.infrastructure.email.Email;
import com.funck.digitalbank.infrastructure.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class PropostaRecusadaListenerTest {

    @InjectMocks
    private PropostaRecusadaListener propostaRecusadaListener;

    @Spy
    private BancoConfig bancoConfig;

    @Mock
    private EmailSender emailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcederProposta() {
        // given
        var propostaRecusadaEvent = new PropostaRecusadaEvent(PropostaContaEventPublisher.class, getPropostaConta());

        // when
        propostaRecusadaListener.procederProposta(propostaRecusadaEvent);

        // then
        ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);

        verify(emailSender).send(emailArgumentCaptor.capture());

        var email = emailArgumentCaptor.getValue();

        assertAll("email", () -> {
            assertEquals("banco@gmail.com", email.getEmitente());
            assertEquals("pessoa@gmail.com", email.getDestinatario());
            assertEquals("Por favor, aceita nossa proposta para abrir um conta digital : ) ", email.getMensagem());
            assertEquals("Você recusou nossa proposta de abertura de conta", email.getTitulo());
        });
    }

    public PropostaConta getPropostaConta() {
        var pessoa = Pessoa.builder().email("pessoa@gmail.com").nome("Felipe").sobrenome("Funck").cpf("000.000.000-00").build();

        var proposta = new PropostaConta();
        proposta.setPessoa(pessoa);

        return proposta;
    }

}
