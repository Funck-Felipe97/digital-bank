package com.funck.digitalbank.infrastructure.integration;

import com.funck.digitalbank.domain.model.FotoCPF;

public interface ValidaDocumentoPessoa {

    boolean isValido(FotoCPF fotoCPF);

}
