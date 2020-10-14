package com.funck.digitalbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "token_acesso")
public class TokenAcesso extends AbstractEntity {

    @Size(min = 6, max = 6)
    @NotBlank
    private String token;

    @NotNull
    private LocalDateTime dataValidade;

    @NotNull
    @JoinColumn(nullable = false, name = "conta_id")
    @ManyToOne(optional = false)
    private Conta conta;

    @NotNull
    private Boolean usado;

    @NotNull
    private Boolean validado;

    public boolean expirado() {
        return LocalDateTime.now().isAfter(dataValidade);
    }

}
