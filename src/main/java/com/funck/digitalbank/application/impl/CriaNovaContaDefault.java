package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.CriaNovaConta;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class CriaNovaContaDefault implements CriaNovaConta {

    private final ContaRepository contaRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void criar(@NotNull final PropostaConta propostaConta) {
        var conta = gerarConta(propostaConta);

        contaRepository.save(conta);
    }

    private Conta gerarConta(final PropostaConta propostaConta) {
        return Conta
                .builder()
                .proposta(propostaConta)
                .agencia("1234")
                .numero("12345678")
                .banco("123")
                .saldo(new BigDecimal("0.00"))
                .build();
    }

}
