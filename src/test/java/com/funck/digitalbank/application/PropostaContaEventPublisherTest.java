package com.funck.digitalbank.application;

import com.funck.digitalbank.application.events.PropostaAceitaEvent;
import com.funck.digitalbank.application.events.PropostaRecusadaEvent;
import com.funck.digitalbank.application.impl.PropostaContaEventPublisherDefault;
import com.funck.digitalbank.domain.model.PropostaConta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class PropostaContaEventPublisherTest {

    @InjectMocks
    private PropostaContaEventPublisherDefault propostaContaEventPublisher;

    @Mock
    private ApplicationEventPublisher publisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve publicar evento de proposta recusada")
    public void testPublishPropostaRecusadaEvent() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");

        // when
        propostaContaEventPublisher.publishPropostaRecusadaEvent(proposta);

        // then
        ArgumentCaptor<PropostaRecusadaEvent> captor = ArgumentCaptor.forClass(PropostaRecusadaEvent.class);

        verify(publisher).publishEvent(captor.capture());

        assertEquals(proposta, captor.getValue().getPropostaConta());
    }

    @Test
    @DisplayName("Deve publicar evento de proposta aceita")
    public void testpublishPropostaAceitaEvent() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");

        // when
        propostaContaEventPublisher.publishPropostaAceitaEvent(proposta);

        // then
        ArgumentCaptor<PropostaAceitaEvent> captor = ArgumentCaptor.forClass(PropostaAceitaEvent.class);

        verify(publisher).publishEvent(captor.capture());

        assertEquals(proposta, captor.getValue().getPropostaConta());
    }

}
