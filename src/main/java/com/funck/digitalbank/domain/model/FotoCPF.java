package com.funck.digitalbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "foto_cpf")
public class FotoCPF extends AbstractEntity {

    @NotNull
    @Column(name = "foto_frente", nullable = false)
    private String fotoFrente;

    @NotNull
    @Column(name = "foto_verso", nullable = false)
    private String fotoVerso;

}
