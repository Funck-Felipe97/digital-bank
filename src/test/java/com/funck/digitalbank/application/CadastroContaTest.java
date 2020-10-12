package com.funck.digitalbank.application;

import com.funck.digitalbank.application.events.ContaCriadaEvent;
import com.funck.digitalbank.application.impl.CadastroContaDefault;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CadastroContaTest {

    @InjectMocks
    private CadastroContaDefault criaNovaConta;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando já existir uma conta para a porposta informada")
    public void testCriar() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");

        doReturn(true).when(contaRepository).existsByProposta_id("idProposta");

        // when then
        assertThrows(IllegalArgumentException.class, () -> criaNovaConta.criar(proposta));
    }

    @Test
    @DisplayName("Deve criar uma nova conta quando a porposta for válida")
    public void testCriar2() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");

        // when
        criaNovaConta.criar(proposta);

        // then
        var contaCaptor = ArgumentCaptor.forClass(Conta.class);

        verify(contaRepository).save(contaCaptor.capture());

        var conta = contaCaptor.getValue();

        assertAll("conta", () -> {
            assertEquals(proposta, conta.getProposta());
            assertEquals("123", conta.getBanco());
            assertEquals(8, conta.getNumero().length());
            assertEquals(4, conta.getAgencia().length());
            assertEquals(new BigDecimal("0.00"), conta.getSaldo());
        });
    }

    @Test
    @DisplayName("Deve publicar um evento de conta criada quando ela for criada com sucesso")
    public void testCriar3() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");

        // when
        criaNovaConta.criar(proposta);

        // then
        verify(publisher).publishEvent(any(ContaCriadaEvent.class));
    }

    @Test
    @DisplayName("Não deve publicar evento de conta criada quando ocorrer algum erro")
    public void testCriar4() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");

        doReturn(true).when(contaRepository).existsByProposta_id("idProposta");

        // when then
        assertThrows(IllegalArgumentException.class, () -> criaNovaConta.criar(proposta));

        verifyNoInteractions(publisher);
    }

}
