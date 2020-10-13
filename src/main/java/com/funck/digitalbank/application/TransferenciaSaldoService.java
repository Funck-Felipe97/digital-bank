package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.NovaTransferencia;

import java.util.Set;

public interface TransferenciaSaldoService<T extends NovaTransferencia> {

    void salvarTransferencia(Set<T> transferencias);

    void salvarTransferencia(T novaTransferencia);

}
