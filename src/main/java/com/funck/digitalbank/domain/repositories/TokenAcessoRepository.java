package com.funck.digitalbank.domain.repositories;

import com.funck.digitalbank.domain.model.TokenAcesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenAcessoRepository extends JpaRepository<TokenAcesso, String> {

}
