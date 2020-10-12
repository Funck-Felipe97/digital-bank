package com.funck.digitalbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "conta")
public class Conta extends AbstractEntity {

    @Column(nullable = false, name = "agencia", length = 4)
    private String agencia;

    @Column(nullable = false, name = "numero", length = 8)
    private String numero;

    @Column(nullable = false, name = "banco", length = 3)
    private String banco;

    @JoinColumn(name = "proposta_id", nullable = false)
    @OneToOne(optional = false)
    private PropostaConta proposta;

    @Column(name = "saldo", nullable = false, scale = 2)
    private BigDecimal saldo;

    private String senha;

}
