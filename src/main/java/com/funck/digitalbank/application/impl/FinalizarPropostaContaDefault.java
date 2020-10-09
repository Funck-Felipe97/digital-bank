package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.FinalizarPropostaConta;
import com.funck.digitalbank.domain.model.PropostaConta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FinalizarPropostaContaDefault implements FinalizarPropostaConta {

    @Override
    public void rejeitarProposta(PropostaConta propostaConta) {

    }

    @Override
    public void aceitarProposta(PropostaConta propostaConta) {

    }

}
