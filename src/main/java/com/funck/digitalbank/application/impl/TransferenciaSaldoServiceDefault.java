package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TransferenciaExecutor;
import com.funck.digitalbank.application.TransferenciaSaldoService;
import com.funck.digitalbank.domain.model.NovaTransferencia;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferenciaSaldoServiceDefault implements TransferenciaSaldoService<NovaTransferencia> {

    private final ContaRepository contaRepository;
    private final TransferenciaExecutor transferenciaExecutor;

    @Override
    public void salvarTransferencia(final Set<NovaTransferencia> transferencias) {
        transferencias.forEach(this::salvarTransferencia);
    }

    @Async
    @Override
    public void salvarTransferencia(final NovaTransferencia novaTransferencia) {
        contaRepository.findByAgenciaAndNumero(novaTransferencia.getAgenciaDestino(), novaTransferencia.getContaDestino())
                .ifPresentOrElse(conta -> {
                    transferenciaExecutor.execute(conta, novaTransferencia.toTransferenciaSaldo());
                }, () -> {
                    log.info("Conta não encontrada, agência: (%s), conta: (%s)", novaTransferencia.getAgenciaDestino(), novaTransferencia.getContaDestino());
                });
    }

}
