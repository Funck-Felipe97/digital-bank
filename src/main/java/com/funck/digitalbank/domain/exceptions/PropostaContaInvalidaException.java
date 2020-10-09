package com.funck.digitalbank.domain.exceptions;

public class PropostaContaInvalidaException extends RuntimeException {

    public PropostaContaInvalidaException() {
        super("Proposta inválida");
    }

    public PropostaContaInvalidaException(String message) {
        super(message);
    }

}
