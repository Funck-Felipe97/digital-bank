package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/token")
@RestController
public class TokenValidatorResource implements AbstractResource {

    private final TokenValidator tokenValidator;

    @GetMapping("/{contaId}")
    public void validarToken(@PathVariable final String contaId, @RequestParam final String token) {
        tokenValidator.validar(contaId, token);
    }

}
