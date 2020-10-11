package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.CriaNovaConta;
import com.funck.digitalbank.application.FinalizarPropostaConta;
import com.funck.digitalbank.application.events.PropostaAceitaEvent;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.model.StatusProposta;
import com.funck.digitalbank.domain.repositories.PropostaContaRepository;
import com.funck.digitalbank.infrastructure.integration.ValidaDocumentoPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class PropostaAceitaListener implements FinalizarPropostaConta<PropostaAceitaEvent> {

    private final ValidaDocumentoPessoa validaDocumentoPessoa;
    private final PropostaContaRepository propostaContaRepository;
    private final CriaNovaConta criaNovaConta;

    @Transactional(propagation = Propagation.REQUIRED)
    @EventListener
    @Override
    public void procederProposta(final PropostaAceitaEvent propostaAceitaEvent) {
        var propostaConta = propostaAceitaEvent.getPropostaConta();

        propostaConta.validarPropostaFinalizada();

        validarDocumentoPessoa(propostaConta);

        if (StatusProposta.LIBERADA.equals(propostaConta.getStatusProposta())) {
            criaNovaConta.criar(propostaConta);
        }
    }

    private void validarDocumentoPessoa(final PropostaConta propostaConta) {
        boolean documentoValido = validaDocumentoPessoa.isValido(propostaConta.getPessoa().getFotoCpf());

        if (documentoValido) {
            propostaConta.setStatusProposta(StatusProposta.LIBERADA);
        } else {
            propostaConta.setStatusProposta(StatusProposta.PENDENTE);
        }

        propostaContaRepository.save(propostaConta);
    }

}
