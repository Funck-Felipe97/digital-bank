package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.TransferenciaExecutorDefault;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import com.funck.digitalbank.domain.repositories.TransferenciaSaldoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TransferenciaExecutorTest {

    @InjectMocks
    private TransferenciaExecutorDefault transferenciaExecutor;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TransferenciaSaldoRepository transferenciaSaldoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Não deve realizar a transfência quando ela já estiver sido realizada anteriormente")
    public void testeExecute() {
        // given
        var conta = new Conta();
        conta.setId("idConta");

        var transferenciaSaldo = new TransferenciaSaldo();
        transferenciaSaldo.setCodigoTransferencia("000");
        transferenciaSaldo.setProcessada(true);

        doReturn(Optional.of(transferenciaSaldo)).when(transferenciaSaldoRepository).findByCodigoTransferencia("000");

        // when
        transferenciaExecutor.execute(conta, transferenciaSaldo);

        // then
        verify(transferenciaSaldoRepository, times(0)).save(transferenciaSaldo);
        verify(contaRepository, times(0)).save(conta);
    }

    @Test
    @DisplayName("Deve realizar a transferência quando as informações estiverem corretas")
    public void testeExecute2() {
        // given
        var conta = new Conta();
        conta.setId("idConta");

        var transferenciaSaldo = new TransferenciaSaldo();
        transferenciaSaldo.setCodigoTransferencia("000");
        transferenciaSaldo.setProcessada(false);
        transferenciaSaldo.setValorTransferencia(new BigDecimal(10.00));

        // when
        transferenciaExecutor.execute(conta, transferenciaSaldo);

        // then
        var contaCaptor = ArgumentCaptor.forClass(Conta.class);
        var transferenciaCaptor = ArgumentCaptor.forClass(TransferenciaSaldo.class);

        verify(transferenciaSaldoRepository).save(transferenciaCaptor.capture());
        verify(contaRepository).save(contaCaptor.capture());

        assertAll("transferencia", () -> {
            assertEquals(new BigDecimal(10.00), contaCaptor.getValue().getSaldo());
            assertEquals(conta, transferenciaCaptor.getValue().getContaDestino());
            assertTrue(transferenciaCaptor.getValue().getProcessada());
        });
    }

}
