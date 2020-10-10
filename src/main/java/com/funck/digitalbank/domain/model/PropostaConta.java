package com.funck.digitalbank.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "proposta_conta")
public class PropostaConta extends AbstractEntity {

    @JoinColumn(name = "pessoa_id")
    @OneToOne(optional = false)
    private Pessoa pessoa;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "etapa_proposta", nullable = false)
    private EtapaCriacaoProposta etapaProposta;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_proposta")
    private StatusProposta statusProposta;

    public static PropostaConta novaProposta(final Pessoa pessoa) {
        PropostaConta propostaConta = new PropostaConta();

        propostaConta.pessoa = pessoa;
        propostaConta.etapaProposta = EtapaCriacaoProposta.PESSOA_CADASTRADA;

        return propostaConta;
    }

    public void validarEtapasAnteriores() {
        etapaProposta.validar(this);
    }

}
