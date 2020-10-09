package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.PropostaConta;

public interface FinalizarPropostaConta {

    void rejeitarProposta(PropostaConta propostaConta);

    void aceitarProposta(PropostaConta propostaConta);

}
