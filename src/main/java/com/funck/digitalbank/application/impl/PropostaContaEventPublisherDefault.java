package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.PropostaContaEventPublisher;
import com.funck.digitalbank.application.events.PropostaAceitaEvent;
import com.funck.digitalbank.application.events.PropostaRecusadaEvent;
import com.funck.digitalbank.domain.model.PropostaConta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PropostaContaEventPublisherDefault implements PropostaContaEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishPropostaRecusadaEvent(final PropostaConta propostaConta) {
        log.info("Publicando evento de proposta recusada");

        var propostaRecusadaEvent = new PropostaRecusadaEvent(this, propostaConta);

        publisher.publishEvent(propostaRecusadaEvent);
    }

    @Override
    public void publishPropostaAceitaEvent(final PropostaConta propostaConta) {
        log.info("Publicando evento de proposta aceita");

        var propostaAceitaEvent = new PropostaAceitaEvent(this, propostaConta);

        publisher.publishEvent(propostaAceitaEvent);
    }

}
