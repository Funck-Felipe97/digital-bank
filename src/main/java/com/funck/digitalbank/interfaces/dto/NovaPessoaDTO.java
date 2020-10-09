package com.funck.digitalbank.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funck.digitalbank.domain.model.Pessoa;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class NovaPessoaDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Past
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

    public Long getIdade() {
        return  ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());
    }

}
