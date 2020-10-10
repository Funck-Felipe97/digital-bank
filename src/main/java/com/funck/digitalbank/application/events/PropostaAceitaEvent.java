package com.funck.digitalbank.application.events;

import com.funck.digitalbank.domain.model.PropostaConta;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PropostaAceitaEvent extends ApplicationEvent implements PropostaContaEvent {

    private final PropostaConta propostaConta;

    public PropostaAceitaEvent(Object source, PropostaConta propostaConta) {
        super(source);
        this.propostaConta = propostaConta;
    }

}
