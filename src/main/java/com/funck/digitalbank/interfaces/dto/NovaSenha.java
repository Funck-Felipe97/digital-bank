package com.funck.digitalbank.interfaces.dto;

import com.funck.digitalbank.domain.model.SenhaInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NovaSenha implements SenhaInfo {

    @NotBlank
    private String senha;

    @NotBlank
    private String contaId;

    @NotBlank
    private String tokenAcessoId;

}
