package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.NovaTransferencia;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;

import java.util.Set;

public interface TransferenciaSaldoService<T extends NovaTransferencia> {

    void salvarTransferencia(Set<T> transferencias);

    void salvarTransferencia(T novaTransferencia);

    void salvarTransferencia(final Conta conta, final TransferenciaSaldo transferenciaSaldo);

}
