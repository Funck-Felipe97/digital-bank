package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.NovaPropostaConta;
import com.funck.digitalbank.interfaces.dto.NovaFotoCPF;
import com.funck.digitalbank.interfaces.dto.NovaPessoa;
import com.funck.digitalbank.interfaces.dto.NovoEndereco;
import com.funck.digitalbank.interfaces.dto.PessoaInfo;
import com.funck.digitalbank.interfaces.dto.RespostaProposta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/proposta-conta")
@RestController
public class PropostaContaResource implements AbstractResource {

    private final NovaPropostaConta novaPropostaConta;

    @PostMapping
    public ResponseEntity criarProposta(@RequestBody @Valid final NovaPessoa novaPessoa) {
        var pessoa = novaPessoa.toPessoa();

        var proposta = novaPropostaConta.novaProposta(pessoa);

        return ResponseEntity
                .created(createURI("proposta-conta/{id}/endereco", proposta.getId()))
                .build();
    }

    @PostMapping("/{propostaId}/endereco")
    public ResponseEntity cadastrarEndereco(
            @PathVariable final String propostaId,
            @RequestBody @Valid final NovoEndereco novoEndereco) {
        var endereco = novoEndereco.toEndereco();

        var proposta = novaPropostaConta.cadastrarEndereco(propostaId, endereco);

        return ResponseEntity
                .created(createURI("proposta-conta/{id}/cpf", proposta.getId()))
                .build();
    }

    @PostMapping("/{propostaId}/cpf")
    public ResponseEntity<PessoaInfo> cadastrarCPF(
            @PathVariable final String propostaId,
            @RequestBody @Valid NovaFotoCPF novaFotoCPF) {
        var fotoCPF = novaFotoCPF.toCPF();

        var proposta = novaPropostaConta.cadastrarFotoCPF(propostaId, fotoCPF);

        return ResponseEntity
                .created(createURI("proposta-conta/{id}/finalizar", proposta.getId()))
                .body(new PessoaInfo(proposta.getPessoa()));
    }

    @PostMapping("/{propostaId}/finalizar")
    public ResponseEntity finalizarProposta(
            @PathVariable final String propostaId,
            @RequestBody @Valid final RespostaProposta respostaProposta) {
        var proposta = novaPropostaConta.finalizarProposta(propostaId, respostaProposta.isPropostaAceita());

        var message = !respostaProposta.isPropostaAceita() ? ": (" : "A conta está sendo criada : )";

        return ResponseEntity.ok(message);
    }

}
