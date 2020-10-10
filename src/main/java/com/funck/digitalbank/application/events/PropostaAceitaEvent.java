package com.funck.digitalbank.application.events;

import com.funck.digitalbank.domain.model.PropostaConta;
import org.springframework.context.ApplicationEvent;

public class PropostaAceitaEvent extends ApplicationEvent {

    private final PropostaConta propostaConta;

    public PropostaAceitaEvent(Object source, PropostaConta propostaConta) {
        super(source);
        this.propostaConta = propostaConta;
    }

}
