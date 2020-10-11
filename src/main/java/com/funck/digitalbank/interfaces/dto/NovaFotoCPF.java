package com.funck.digitalbank.interfaces.dto;

import com.funck.digitalbank.domain.model.FotoCPF;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NovaFotoCPF {

    @NotNull
    private String frente;

    @NotNull
    private String verso;

    public FotoCPF toCPF() {
        return FotoCPF.builder()
                .fotoFrente(frente)
                .fotoVerso(verso)
                .build();
    }

}
