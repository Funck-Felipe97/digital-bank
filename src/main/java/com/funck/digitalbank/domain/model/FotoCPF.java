package com.funck.digitalbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "foto_cpf")
public class FotoCPF extends AbstractEntity {

    @Lob
    @Column(name = "foto_frente")
    private byte[] fotoFrente;

    @Lob
    @Column(name = "foto_verso")
    private byte[] fotoVerso;

}
