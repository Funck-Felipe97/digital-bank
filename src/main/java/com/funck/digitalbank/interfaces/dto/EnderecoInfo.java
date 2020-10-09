package com.funck.digitalbank.interfaces.dto;

import com.funck.digitalbank.domain.model.Endereco;
import lombok.Getter;

@Getter
public class EnderecoInfo {

    private final String cep;
    private final String rua;
    private final String bairro;
    private final String complemento;
    private final String cidade;
    private final String estado;

    public EnderecoInfo(Endereco endereco) {
        cep = endereco.getCep();
        rua = endereco.getRua();
        bairro = endereco.getBairro();
        complemento = endereco.getComplemento();
        cidade = endereco.getCidade();
        estado = endereco.getEstado();
    }

}
