package com.funck.digitalbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "transferencia")
public class TransferenciaSaldo extends AbstractEntity {

    @JoinColumn(nullable = false, name = "conta_destino_id")
    @ManyToOne(optional = false)
    private Conta contaDestino;

    @NotBlank
    @Column(nullable = false, name = "codigo_transferencia")
    private String codigoTransferencia;

    @NotNull
    @Min(0)
    @Column(nullable = false, name = "valor_transferencia", scale = 2)
    private BigDecimal valorTransferencia;

    @NotNull
    @Column(nullable = false, name = "data_transferencia")
    private LocalDateTime dataTransferencia;

    @NotBlank
    @Column(nullable = false, name = "documento_origem")
    private String documentoOrigem;

    @NotBlank
    @Column(nullable = false, name = "banco_origem")
    private String bancoOrigem;

    @NotBlank
    @Column(nullable = false, name = "conta_origem")
    private String contaOrigem;

    @NotBlank
    @Column(nullable = false, name = "agencia_origem")
    private String agenciaOrigem;

    @NotNull
    @Column(nullable = false, name = "transferencia_processada")
    private Boolean processada;

}
