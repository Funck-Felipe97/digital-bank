package com.funck.digitalbank.infrastructure.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Email {

    private String emitente;
    private String destinatario;
    private String mensagem;
    private String titulo;

}
