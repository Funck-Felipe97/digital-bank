package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.CadastroSenha;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TokenAcesso;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.domain.repositories.TokenAcessoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CadastroSenhaDefault implements CadastroSenha {

    private final TokenAcessoRepository tokenAcessoRepository;
    private final ContaRepository contaRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void criarSenha(final String contaId, String senha) {
        var conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new NoSuchElementException("Conta não encontrada: " + contaId));

        var tokenAcesso = tokenAcessoRepository.findTokenValidoByConta(conta)
                .orElseThrow(() -> new NoSuchElementException("A conta não possuí o token válido associoado"));

        validarToken(tokenAcesso);

        definirSenha(conta, senha);

        atualizarToken(tokenAcesso);
    }

    private void validarToken(final TokenAcesso tokenAcesso) {
        if (!tokenAcesso.getValidado()) {
            throw new IllegalArgumentException("O token informado não foi validado");
        }

        if (tokenAcesso.getUsado()) {
            throw new IllegalArgumentException("O token informado já foi utilizado");
        }
    }

    private void definirSenha(final Conta conta, final String senha) {
        conta.setSenha(DigestUtils.md5DigestAsHex(senha.getBytes()));

        contaRepository.save(conta);
    }

    private void atualizarToken(final TokenAcesso tokenAcesso) {
        tokenAcesso.setUsado(true);

        tokenAcessoRepository.save(tokenAcesso);
    }

}
