package com.funck.digitalbank.interfaces.dto;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;

@Data
public class PrimeiroAcessoInfo {

    @Email
    private String email;

    @CPF
    private String cpf;

}
