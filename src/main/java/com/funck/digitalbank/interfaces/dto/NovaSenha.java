package com.funck.digitalbank.interfaces.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NovaSenha {

    @NotBlank
    private String senha;

}
