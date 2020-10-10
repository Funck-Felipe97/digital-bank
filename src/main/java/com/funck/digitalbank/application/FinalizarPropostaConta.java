package com.funck.digitalbank.application;

import com.funck.digitalbank.application.events.PropostaContaEvent;

public interface FinalizarPropostaConta<T extends PropostaContaEvent> {

    void procederProposta(T propostaContaEvent);

}
