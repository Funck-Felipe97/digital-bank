package com.funck.digitalbank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface NovaTransferencia {

    BigDecimal getValorTransferencia();

    LocalDateTime getDataTransferencia();

    String getDocumentoOrigem();

    String getBancoOrigem();

    String getContaOrigem();

    String getAgenciaOrigem();

    String getCodigoTransferencia();

    String getContaDestino();

    String getAgenciaDestino();

    TransferenciaSaldo toTransferenciaSaldo();

}
