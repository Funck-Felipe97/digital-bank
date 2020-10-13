package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;

public interface TransferenciaExecutor {

    void execute(Conta conta, TransferenciaSaldo transferenciaSaldo);

}
