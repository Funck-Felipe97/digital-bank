package com.funck.digitalbank.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funck.digitalbank.domain.model.NovaTransferencia;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = {"codigoTransferencia"})
@Data
public class TransferenciaInfo implements NovaTransferencia {

    @NotBlank
    private String codigoTransferencia;

    @NotNull
    @Min(0)
    private BigDecimal valorTransferencia;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime dataTransferencia;

    @NotBlank
    private String documentoOrigem;

    @NotBlank
    private String bancoOrigem;

    @NotBlank
    private String contaOrigem;

    @NotBlank
    private String agenciaOrigem;

    @NotBlank
    private String contaDestino;

    @NotBlank
    private String agenciaDestino;

    @Override
    public TransferenciaSaldo toTransferenciaSaldo() {
        return TransferenciaSaldo.builder()
                .valorTransferencia(valorTransferencia)
                .dataTransferencia(dataTransferencia)
                .documentoOrigem(documentoOrigem)
                .contaOrigem(contaOrigem)
                .agenciaOrigem(agenciaOrigem)
                .bancoOrigem(bancoOrigem)
                .codigoTransferencia(codigoTransferencia)
                .processada(false)
                .build();
    }
}
