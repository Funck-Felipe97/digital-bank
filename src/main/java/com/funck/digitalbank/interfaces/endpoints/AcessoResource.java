package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.AcessoConta;
import com.funck.digitalbank.interfaces.dto.PrimeiroAcessoInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/primeiro-acesso")
@RestController
public class AcessoResource implements AbstractResource {

    private final AcessoConta acessoConta;

    @PostMapping
    public ResponseEntity<String> primeiroAcesso(@RequestBody @Valid final PrimeiroAcessoInfo primeiroAcessoInfo, HttpServletResponse response) {
        var tokenAcesso = acessoConta.primeiroAcesso(primeiroAcessoInfo.getEmail(), primeiroAcessoInfo.getCpf());

        response.setHeader("Location", createURI("token/{id}", tokenAcesso.getConta().getId()).toASCIIString());

        return ResponseEntity.ok("Enviamos um token de acesso para o seu email");
    }

}
