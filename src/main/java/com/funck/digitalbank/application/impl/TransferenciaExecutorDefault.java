package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TransferenciaExecutor;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.domain.repositories.TransferenciaSaldoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Primary
@Service
public class TransferenciaExecutorDefault implements TransferenciaExecutor {

    private final ContaRepository contaRepository;
    private final TransferenciaSaldoRepository transferenciaSaldoRepository;

    @Override
    public void execute(final Conta conta, final TransferenciaSaldo transferencia) {
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