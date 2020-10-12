package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/token")
@RestController
public class TokenValidatorResource implements AbstractResource {

    private final TokenValidator tokenValidator;

    @GetMapping("/{contaId}")
    public void validarToken(
            @PathVariable final String contaId,
            @RequestParam final String token,
            final HttpServletResponse response) {
        tokenValidator.validar(contaId, token);

        response.setHeader("Location", createURI("conta/{id}/senha", contaId).toASCIIString());
    }

}
