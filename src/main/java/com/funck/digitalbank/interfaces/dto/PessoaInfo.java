package com.funck.digitalbank.interfaces.dto;

import com.funck.digitalbank.domain.model.Pessoa;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PessoaInfo {

    private final String nome;
    private final String sobrenome;
    private final String email;
    private final LocalDate dataNascimento;
    private final String cpf;
    private final EnderecoInfo endereco;

    public PessoaInfo(Pessoa pessoa) {
        nome = pessoa.getNome();
        sobrenome = pessoa.getSobrenome();
        email = pessoa.getEmail();
        dataNascimento = pessoa.getDataNascimento();
        cpf = pessoa.getCpf();
        endereco = new EnderecoInfo(pessoa.getEndereco());
    }

}
