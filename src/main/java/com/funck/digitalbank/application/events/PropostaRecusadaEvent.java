package com.funck.digitalbank.application.events;

import com.funck.digitalbank.domain.model.PropostaConta;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PropostaRecusadaEvent extends ApplicationEvent {

    private final PropostaConta propostaConta;

    public PropostaRecusadaEvent(Object source, PropostaConta propostaConta) {
        super(source);
        this.propostaConta = propostaConta;
    }

}
