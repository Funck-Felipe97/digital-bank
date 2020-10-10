package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.CriaNovaConta;
import com.funck.digitalbank.application.FinalizarPropostaConta;
import com.funck.digitalbank.application.events.PropostaAceitaEvent;
import com.funck.digitalbank.domain.model.StatusProposta;
import com.funck.digitalbank.domain.repositories.PropostaContaRepository;
import com.funck.digitalbank.infrastructure.integration.ValidaDocumentoPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PropostaAceitaListener implements FinalizarPropostaConta<PropostaAceitaEvent> {

    private final ValidaDocumentoPessoa validaDocumentoPessoa;
    private final PropostaContaRepository propostaContaRepository;
    private final CriaNovaConta criaNovaConta;

    @EventListener
    @Override
    public void procederProposta(final PropostaAceitaEvent propostaAceitaEvent) {
        var propostaConta = propostaAceitaEvent.getPropostaConta();

        propostaConta.validarPropostaFinalizada();

        boolean documentoValido = validaDocumentoPessoa.isValido(propostaConta.getPessoa().getFotoCpf());

        if (documentoValido) {
            propostaConta.setStatusProposta(StatusProposta.LIBERADA);
        } else {
            propostaConta.setStatusProposta(StatusProposta.PENDENTE);
        }

        propostaContaRepository.save(propostaConta);

        if (documentoValido) {
            criaNovaConta.criar(propostaConta);
        }
    }
}
