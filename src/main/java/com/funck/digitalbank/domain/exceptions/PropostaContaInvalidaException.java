package com.funck.digitalbank.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PropostaContaInvalidaException extends RuntimeException {

    public PropostaContaInvalidaException() {
        super("Proposta inválida");
    }

    public PropostaContaInvalidaException(String message) {
        super(message);
    }

}
