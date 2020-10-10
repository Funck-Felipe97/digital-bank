package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.NovaPropostaConta;
import com.funck.digitalbank.application.PropostaContaEventPublisher;
import com.funck.digitalbank.domain.model.Endereco;
import com.funck.digitalbank.domain.model.EtapaCriacaoProposta;
import com.funck.digitalbank.domain.model.FotoCPF;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.repositories.PessoaRepository;
import com.funck.digitalbank.domain.repositories.PropostaContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class NovaPropostaContaDefault implements NovaPropostaConta {

    private final PropostaContaRepository propostaContaRepository;
    private final PessoaRepository pessoaRepository;
    private final PropostaContaEventPublisher propostaContaEventPublisher;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PropostaConta novaProposta(final Pessoa pessoa) {
        var pessoaSalva = pessoaRepository.save(pessoa);

        var propostaConta = PropostaConta.novaProposta(pessoaSalva);

        return propostaContaRepository.save(propostaConta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PropostaConta cadastrarEndereco(final String propostaId, final Endereco endereco) {
        var proposta = getProposta(propostaId);

        proposta.validarEtapasAnteriores();

        proposta.getPessoa().setEndereco(endereco);

        pessoaRepository.save(proposta.getPessoa());

        proposta.setEtapaProposta(EtapaCriacaoProposta.ENDERECO_CADASTRADO);

        return propostaContaRepository.save(proposta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PropostaConta cadastrarFotoCPF(final String propostaId, final FotoCPF fotoCPF) {
        var proposta = getProposta(propostaId);

        proposta.validarEtapasAnteriores();

        proposta.getPessoa().setFotoCpf(fotoCPF);

        pessoaRepository.save(proposta.getPessoa());

        proposta.setEtapaProposta(EtapaCriacaoProposta.CPF_CADASTRADO);

        return propostaContaRepository.save(proposta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PropostaConta finalizarProposta(final String propostaId, final boolean propostaAceita) {
        var proposta = getProposta(propostaId);

        proposta.validarEtapasAnteriores();

        proposta.setEtapaProposta(EtapaCriacaoProposta.PROPOSTA_FINALIZADA);

        proposta = propostaContaRepository.save(proposta);

        if (propostaAceita) {
            propostaContaEventPublisher.publishPropostaAceitaEvent(proposta);
        } else {
            propostaContaEventPublisher.publishPropostaRecusadaEvent(proposta);
        }

        return proposta;
    }

    private PropostaConta getProposta(String propostaId) {
        return propostaContaRepository.findById(propostaId)
                .orElseThrow(() -> new NoSuchElementException("Proposta não encontrada: " + propostaId));
    }

}
