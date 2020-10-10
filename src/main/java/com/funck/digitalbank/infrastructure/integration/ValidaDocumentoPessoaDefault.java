package com.funck.digitalbank.infrastructure.integration;

import com.funck.digitalbank.domain.model.FotoCPF;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidaDocumentoPessoaDefault implements ValidaDocumentoPessoa {

    @Override
    public boolean isValido(final FotoCPF fotoCPF) {
        int tentativas = 0;

        while (tentativas < 3) {
            try {
                log.info("Chamando algum serviço externo que valida cpf");
                return true;
            } catch (Exception e) {
                tentativas += 1;
            }
        }

        return false;
    }

}
