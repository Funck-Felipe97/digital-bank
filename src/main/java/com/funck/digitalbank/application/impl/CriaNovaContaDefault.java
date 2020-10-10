package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.CriaNovaConta;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.domain.repositories.PropostaContaRepository;
import com.funck.digitalbank.infrastructure.integration.ValidaDocumentoPessoa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Slf4j
@RequiredArgsConstructor
@Component
public class CriaNovaContaDefault implements CriaNovaConta {

    private final ValidaDocumentoPessoa validaDocumentoPessoa;
    private final ContaRepository contaRepository;
    private final PropostaContaRepository propostaContaRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void criar(@NotNull final PropostaConta propostaConta) {

    }

}
