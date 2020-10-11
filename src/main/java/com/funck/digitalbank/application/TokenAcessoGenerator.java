package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;

public interface TokenAcessoGenerator {

    TokenAcesso criarToken(Conta conta);

}
