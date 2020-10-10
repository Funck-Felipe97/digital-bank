package com.funck.digitalbank.application.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaAceitaListener implements ApplicationListener<PropostaAceitaEvent> {

    @Override
    public void onApplicationEvent(PropostaAceitaEvent propostaAceitaEvent) {

    }
}
