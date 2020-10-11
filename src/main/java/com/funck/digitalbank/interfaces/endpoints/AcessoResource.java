package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.AcessoConta;
import com.funck.digitalbank.interfaces.dto.PrimeiroAcessoInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("acesso")
@RestController
public class AcessoResource implements AbstractResource {

    private final AcessoConta acessoConta;

    @PostMapping
    public void primeiroAcesso(@RequestBody @Valid final PrimeiroAcessoInfo primeiroAcessoInfo) {
        acessoConta.primeiroAcesso(primeiroAcessoInfo.getEmail(), primeiroAcessoInfo.getCpf());
    }

}
