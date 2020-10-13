package com.funck.digitalbank.domain.model;

import com.funck.digitalbank.infrastructure.validador.Idade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "pessoa")
public class Pessoa extends AbstractEntity {

    @NotBlank
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank
    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Past
    @Idade(minima = 18)
    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @CPF
    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @JoinColumn(name = "foto_cpf")
    @OneToOne(cascade = CascadeType.ALL)
    private FotoCPF fotoCpf;

    @JoinColumn(name = "endereco_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

}
