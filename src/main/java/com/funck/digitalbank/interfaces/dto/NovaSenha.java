package com.funck.digitalbank.interfaces.dto;

import com.funck.digitalbank.infrastructure.validador.Password;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NovaSenha {

    @Password
    @NotBlank
    private String senha;

}
