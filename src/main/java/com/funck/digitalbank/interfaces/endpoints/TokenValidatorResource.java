package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class TokenValidatorResource implements AbstractResource {

    private final TokenValidator tokenValidator;

    @PostMapping("/conta/{contaId}/token/{token}")
    public void validarToken(@PathVariable final String contaId, @PathVariable final String token, final HttpServletResponse response) {
        tokenValidator.validar(contaId, token);

        response.setHeader("Location", createURI("conta/{id}/senha", contaId).toASCIIString());
    }

}
