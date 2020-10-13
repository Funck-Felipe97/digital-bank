package com.funck.digitalbank.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.infrastructure.validador.CPF;
import com.funck.digitalbank.infrastructure.validador.Email;
import com.funck.digitalbank.infrastructure.validador.Idade;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class NovaPessoa {

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @Email(unique = true)
    @NotNull
    private String email;

    @CPF(unique = true)
    @NotNull
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Idade(minima = 18)
    @Past
    @NotNull
    private LocalDate dataNascimento;

    public Pessoa toPessoa() {
        return Pessoa.builder()
                .nome(nome)
                .sobrenome(sobrenome)
                .dataNascimento(dataNascimento)
                .email(email)
                .cpf(cpf)
                .build();
    }

}
