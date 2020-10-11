package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.TokenAcesso;

public interface AcessoConta {

    TokenAcesso primeiroAcesso(String email, String cpf);

}
