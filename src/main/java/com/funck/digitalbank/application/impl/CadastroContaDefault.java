package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.CadastroConta;
import com.funck.digitalbank.application.events.ContaCriadaEvent;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.infrastructure.util.GeradorDigito;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class CadastroContaDefault implements CadastroConta {

    private final ContaRepository contaRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void criar(@NotNull final PropostaConta propostaConta) {
        log.info("Criando conta para a proposta: " + propostaConta);

        if (contaRepository.existsByProposta_id(propostaConta.getId())) {
            throw new IllegalArgumentException("Já existe uma conta cadastrada para esta proposta");
        }

        var conta = contaRepository.save(gerarConta(propostaConta));

        publisher.publishEvent(new ContaCriadaEvent(this, conta));
    }

    private Conta gerarConta(final PropostaConta propostaConta) {
        return Conta
                .builder()
                .proposta(propostaConta)
                .agencia(GeradorDigito.gerar(4))
                .numero(GeradorDigito.gerar(8))
                .banco("123")
                .saldo(new BigDecimal("0.00"))
                .build();
    }

}
