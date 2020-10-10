package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.PropostaConta;

public interface PropostaContaEventPublisher {

    void publishPropostaRecusadaEvent(PropostaConta propostaConta);

    void publishPropostaAceitaEvent(PropostaConta propostaConta);

}
