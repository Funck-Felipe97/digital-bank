package com.funck.digitalbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "conta")
public class Conta extends AbstractEntity {

    @NotBlank
    @Column(nullable = false, name = "agencia", length = 4)
    private String agencia;

    @NotBlank
    @Column(nullable = false, name = "numero", length = 8)
    private String numero;

    @NotBlank
    @Column(nullable = false, name = "banco", length = 3)
    private String banco;

    @NotNull
    @JoinColumn(name = "proposta_id", nullable = false)
    @OneToOne(optional = false)
    private PropostaConta proposta;

    @NotNull
    @Min(0)
    @Column(name = "saldo", nullable = false, scale = 2)
    private BigDecimal saldo;

    private String senha;

    public Conta() {
        this.saldo = new BigDecimal("0.00");
    }

    public void depositar(@Min(0) @NotNull BigDecimal valorTransferencia) {
        this.saldo = this.saldo.add(valorTransferencia);
    }

}
