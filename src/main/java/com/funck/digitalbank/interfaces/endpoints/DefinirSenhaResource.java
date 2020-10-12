package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.CadastroSenha;
import com.funck.digitalbank.interfaces.dto.NovaSenha;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/cadastro-senha")
@RestController
public class DefinirSenhaResource {

    private final CadastroSenha cadastroSenha;

    @PostMapping
    public void cadastrarSenha(@RequestBody @Valid final NovaSenha novaSenha) {
        cadastroSenha.criarSenha(novaSenha);
    }

}
