package com.funck.digitalbank.interfaces.dto;

import com.funck.digitalbank.domain.model.Endereco;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class NovoEnderecoDTO {

    @NotEmpty
    @Pattern(regexp = "^\\d{5}-\\d{3}")
    private String cep;

    @NotEmpty
    private String rua;

    @NotEmpty
    private String bairro;

    @NotEmpty
    private String complemento;

    @NotEmpty
    private String cidade;

    @NotEmpty
    private String estado;

    public Endereco toEndereco() {
        return Endereco.builder()
                .cep(cep)
                .rua(rua)
                .bairro(bairro)
                .complemento(complemento)
                .cidade(cidade)
                .estado(estado)
                .build();
    }

}
