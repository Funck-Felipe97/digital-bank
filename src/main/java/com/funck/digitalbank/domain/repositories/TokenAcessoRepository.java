package com.funck.digitalbank.domain.repositories;

import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.util.Optional;

public interface TokenAcessoRepository extends JpaRepository<TokenAcesso, String> {

    Optional<TokenAcesso> findByTokenAndConta_id(String token, String contaId);

    @Query("SELECT t FROM TokenAcesso t WHERE t.conta = :conta AND t.usado = FALSE AND t.validado = TRUE")
    Optional<TokenAcesso> findTokenValidoByConta(@PathParam("conta") Conta conta);

}
