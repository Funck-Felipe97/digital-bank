package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.TransferenciaSaldoServiceDefault;
import com.funck.digitalbank.domain.model.Conta;
import com.funck.digitalbank.domain.model.NovaTransferencia;
import com.funck.digitalbank.domain.model.TransferenciaSaldo;
import com.funck.digitalbank.domain.repositories.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

public class TransferenciaSaldoServiceTest {

    @InjectMocks
    private TransferenciaSaldoServiceDefault transferenciaSaldoService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TransferenciaExecutor transferenciaExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Não deve fazer nada quando a conta de destino não existir na base")
    public void testSalvarTransferencia() {
        // given
        var novaTransferencia = mock(NovaTransferencia.class);

        doReturn("codTransferencia").when(novaTransferencia).getCodigoTransferencia();
        doReturn("1111").when(novaTransferencia).getAgenciaDestino();
        doReturn("12345678").when(novaTransferencia).getContaDestino();

        // when
        transferenciaSaldoService.salvarTransferencia(Set.of(novaTransferencia));

        // then
        verify(transferenciaExecutor, times(0)).execute(any(Conta.class), any(TransferenciaSaldo.class));
    }

    @Test
    @DisplayName("Deve executar a transferência quando a conta de destino for encontrada")
    public void testSalvarTransferencia2() {
        // given
        var conta = Conta.builder().agencia("1111").numero("12345678").build();
        var transferenciaSaldo = TransferenciaSaldo.builder().codigoTransferencia("codTransferencia").build();
        var novaTransferencia = mock(NovaTransferencia.class);

        doReturn(Optional.of(conta)).when(contaRepository).findByAgenciaAndNumero("1111", "12345678");

        doReturn("codTransferencia").when(novaTransferencia).getCodigoTransferencia();
        doReturn("1111").when(novaTransferencia).getAgenciaDestino();
        doReturn("12345678").when(novaTransferencia).getContaDestino();
        doReturn(transferenciaSaldo).when(novaTransferencia).toTransferenciaSaldo();

        // when
        transferenciaSaldoService.salvarTransferencia(Set.of(novaTransferencia));

        // then
        verify(transferenciaExecutor).execute(eq(conta), eq(transferenciaSaldo));
    }

}
