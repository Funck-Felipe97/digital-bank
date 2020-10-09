package com.funck.digitalbank.interfaces.dto;

import com.funck.digitalbank.domain.model.FotoCPF;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FotoCpfDTO {

    @NotNull
    private byte[] frente;

    @NotNull
    private byte[] verso;

    public FotoCPF toCPF() {
        return FotoCPF.builder()
                .fotoFrente(frente)
                .fotoVerso(verso)
                .build();
    }

}
