package com.funck.digitalbank.infrastructure.util;

import java.util.Random;

public final class GeradorDigito {

    private GeradorDigito() {

    }

    public static final String gerar(int tamanho) {
        var digitos = new StringBuilder();
        var gerador = new Random();

        for (int i = 0; i < tamanho; ++i) {
            digitos.append(gerador.nextInt(9));
        }

        return digitos.toString();
    }

}
