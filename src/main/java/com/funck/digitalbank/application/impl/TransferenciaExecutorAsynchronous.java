package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.TransferenciaExecutor;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferenciaExecutorAsynchronous implements TransferenciaExecutor {

    private final TransferenciaExecutor transferenciaExecutor;

    @Async
    @Override
    public void execute(final Conta conta, final TransferenciaSaldo transferenciaSaldo) {
        transferenciaExecutor.execute(conta, transferenciaSaldo);
    }

}
