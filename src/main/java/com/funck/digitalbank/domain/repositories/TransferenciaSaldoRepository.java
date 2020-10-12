package com.funck.digitalbank.domain.repositories;

import com.funck.digitalbank.domain.model.TransferenciaSaldo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferenciaSaldoRepository extends JpaRepository<TransferenciaSaldo, String> {

    Optional<TransferenciaSaldo> findByCodigoTransferencia(String codigoTransferencia);

}
