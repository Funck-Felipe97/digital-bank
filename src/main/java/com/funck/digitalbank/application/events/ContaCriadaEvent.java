package com.funck.digitalbank.application.events;

import com.funck.digitalbank.domain.model.Conta;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ContaCriadaEvent extends ApplicationEvent {

    private final Conta conta;

    public ContaCriadaEvent(Object source, Conta conta) {
        super(source);
        this.conta = conta;
    }

}
