package com.funck.digitalbank.domain.repositories;

import com.funck.digitalbank.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, String> {

    boolean existsByProposta_id(String idProposta);

}
