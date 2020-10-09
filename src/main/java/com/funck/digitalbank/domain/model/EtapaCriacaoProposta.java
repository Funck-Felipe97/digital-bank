package com.funck.digitalbank.domain.model;

import com.funck.digitalbank.domain.exceptions.PropostaContaInvalidaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public enum EtapaCriacaoProposta {

    PESSOA_CADASTRADA(1) {

        @Override
        public void validar(@NotNull PropostaConta propostaConta) {
            if (propostaConta.getPessoa() == null) {
                throw new PropostaContaInvalidaException("As informações da pessoa devem estar cadastradas nessa etapa");
            }
        }

    },
    ENDERECO_CADASTRADO(2) {

        @Override
        public void validar(@NotNull PropostaConta propostaConta) {
            PESSOA_CADASTRADA.validar(propostaConta);

            if (propostaConta.getPessoa().getEndereco() == null) {
                throw new PropostaContaInvalidaException("As informações do endereço devem estar cadastradas nessa etapa");
            }
        }

    },
    CPF_CADASTRADO(3) {

        @Override
        public void validar(PropostaConta propostaConta) {
            PESSOA_CADASTRADA.validar(propostaConta);
            ENDERECO_CADASTRADO.validar(propostaConta);

            if (propostaConta.getPessoa().getFotoCpf() == null) {
                throw new PropostaContaInvalidaException("As informações do cpf devem estar cadastradas nessa etapa");
            }
        }

    },
    PROPOSTA_FINALIZADA(4) {

        @Override
        public void validar(PropostaConta propostaConta) {
            PESSOA_CADASTRADA.validar(propostaConta);
            ENDERECO_CADASTRADO.validar(propostaConta);
            CPF_CADASTRADO.validar(propostaConta);

            if (propostaConta.getStatusProposta() == null || StatusProposta.PENDENTE.equals(propostaConta.getStatusProposta())) {
                throw new PropostaContaInvalidaException("A proposta não pode ser finalziada em uma resposta do usuário");
            }
        }

    };

    private final Integer numeroEtapa;

    public abstract void validar(PropostaConta propostaConta);

}
