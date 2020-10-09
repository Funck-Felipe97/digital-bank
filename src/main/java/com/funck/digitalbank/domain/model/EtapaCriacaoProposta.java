package com.funck.digitalbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EtapaCriacaoProposta {

    PESSOA_CADASTRADA(1),
    ENDERECO_CADASTRADO(2),
    CPF_CADASTRADO(3),
    PROPOSTA_FINALIZADA(4);

    private final Integer numeroEtapa;

}
