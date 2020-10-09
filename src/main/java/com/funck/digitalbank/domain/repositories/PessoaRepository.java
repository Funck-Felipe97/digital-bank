package com.funck.digitalbank.domain.repositories;

import com.funck.digitalbank.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PessoaRepository extends JpaRepository<Pessoa, String> {

    boolean existsByEmail(String email);

}
