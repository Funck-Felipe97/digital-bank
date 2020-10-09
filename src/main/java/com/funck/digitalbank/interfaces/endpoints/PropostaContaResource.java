package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.NovaPropostaConta;
import com.funck.digitalbank.interfaces.dto.FotoCpfDTO;
import com.funck.digitalbank.interfaces.dto.NovaPessoaDTO;
import com.funck.digitalbank.interfaces.dto.NovoEnderecoDTO;
import com.funck.digitalbank.interfaces.validator.PessoaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("proposta-conta")
@RestController
public class PropostaContaResource implements AbstractResource {

    private final NovaPropostaConta novaPropostaConta;
    private final PessoaValidator pessoaValidator;

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        binder.addValidators(pessoaValidator);
    }

    @PostMapping
    public ResponseEntity criarProposta(@RequestBody @Valid final NovaPessoaDTO novaPessoaDTO) {
        var pessoa = novaPessoaDTO.toPessoa();

        var proposta = novaPropostaConta.novaProposta(pessoa);

        return ResponseEntity.created(createURI(proposta.getId())).build();
    }

    @PostMapping("/{propostaId}/endereco")
    public ResponseEntity cadastrarEndereco(
            @PathVariable final String propostaId,
            @RequestBody @Valid final NovoEnderecoDTO novoEnderecoDTO
    ) {
        var endereco = novoEnderecoDTO.toEndereco();

        var proposta = novaPropostaConta.cadastrarEndereco(propostaId, endereco);

        return ResponseEntity.created(createURI(proposta.getId())).build();
    }

    @PostMapping("/{propostaId}/cpf")
    public ResponseEntity cadastrarCnh(
            @PathVariable final String propostaId,
            @RequestBody @Valid FotoCpfDTO fotoCpfDTO
    ) {
        var fotoCPF = fotoCpfDTO.toCPF();

        var proposta = novaPropostaConta.cadastrarFotoCPF(propostaId, fotoCPF);

        return ResponseEntity.created(createURI(proposta.getId())).build();
    }

    @PostMapping("/{propostaId}/finalizar")
    public ResponseEntity finalizarProposta(@PathVariable final String propostaId) {
        return null;
    }

}
