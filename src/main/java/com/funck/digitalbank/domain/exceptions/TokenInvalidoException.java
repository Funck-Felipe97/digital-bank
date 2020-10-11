package com.funck.digitalbank.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenInvalidoException extends RuntimeException {

    public TokenInvalidoException() {
        super("Token inválido");
    }

    public TokenInvalidoException(String message) {
        super(message);
    }

}
