package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TransferenciaSaldoService;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.NovaTransferencia;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.domain.repositories.TransferenciaSaldoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferenciaSaldoServiceDefault implements TransferenciaSaldoService<NovaTransferencia> {

    private final ContaRepository contaRepository;
    private final TransferenciaSaldoRepository transferenciaSaldoRepository;

    @Override
    public void salvarTransferencia(final Set<NovaTransferencia> transferencias) {
        transferencias.forEach(this::salvarTransferencia);
    }

    @Override
    public void salvarTransferencia(final NovaTransferencia novaTransferencia) {
        contaRepository.findByAgenciaAndNumero(novaTransferencia.getAgenciaDestino(), novaTransferencia.getContaDestino())
                .ifPresentOrElse(conta -> {
                    salvarTransferencia(conta, novaTransferencia.toTransferenciaSaldo());
                }, () -> {
                    log.info("Conta não encontrada, agência: (%s), conta: (%s)", novaTransferencia.getAgenciaDestino(), novaTransferencia.getContaDestino());
                });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void salvarTransferencia(final Conta conta, final TransferenciaSaldo transferencia) {
        var transferenciaOptional = transferenciaSaldoRepository.findByCodigoTransferencia(transferencia.getCodigoTransferencia());

        if (transferenciaOptional.isPresent() && transferenciaOptional.get().getProcessada()) {
            log.info("Esta transfência já foi processada anteriormente: " + transferencia.getCodigoTransferencia());
            return;
        }

        transferencia.setContaDestino(conta);
        transferencia.setProcessada(true);

        conta.depositar(transferencia.getValorTransferencia());

        transferenciaSaldoRepository.save(transferencia);
        contaRepository.save(conta);
    }

}
