package com.funck.digitalbank.domain.repositories;

import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenAcessoRepository extends JpaRepository<TokenAcesso, String> {

    Optional<TokenAcesso> findByTokenAndConta_id(String token, String contaId);

    Optional<TokenAcesso> findByIdAndConta(String id, Conta conta);

}
