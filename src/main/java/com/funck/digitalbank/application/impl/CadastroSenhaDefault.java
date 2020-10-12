package com.funck.digitalbank.application.impl;

import com.funck.digitalbank.application.CadastroSenha;
import com.funck.digitalbank.domain.model.SenhaInfo;
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
    public void criarSenha(final SenhaInfo senhaInfo) {
        var conta = contaRepository.findById(senhaInfo.getContaId())
                .orElseThrow(() -> new NoSuchElementException("Conta não encontrada: " + senhaInfo.getContaId()));

        var tokenAcesso = tokenAcessoRepository.findByIdAndConta(senhaInfo.getTokenAcessoId(), conta)
                .orElseThrow(() -> new NoSuchElementException("A conta não possuí o token associoado"));

        if (!tokenAcesso.getValidado()) {
            throw new IllegalArgumentException("O token informado não foi validado");
        }

        if (tokenAcesso.getUsado()) {
            throw new IllegalArgumentException("O token informado já foi utilizado");
        }

        conta.setSenha(DigestUtils.md5DigestAsHex(senhaInfo.getSenha().getBytes()));

        contaRepository.save(conta);

        tokenAcesso.setUsado(true);

        tokenAcessoRepository.save(tokenAcesso);
    }

}
