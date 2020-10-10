package com.funck.digitalbank.application;

import com.funck.digitalbank.domain.model.Endereco;
import com.funck.digitalbank.domain.model.FotoCPF;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.domain.model.PropostaConta;

public interface NovaPropostaConta {

    PropostaConta novaProposta(Pessoa pessoa);

    PropostaConta cadastrarEndereco(String propostaId, Endereco endereco);

    PropostaConta cadastrarFotoCPF(String propostaId, FotoCPF fotoCPF);

    PropostaConta finalizarProposta(String propostaId, boolean propostaAceita);

}
