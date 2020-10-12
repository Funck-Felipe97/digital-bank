package com.funck.digitalbank.application;

import com.funck.digitalbank.application.events.PropostaAceitaEvent;
import com.funck.digitalbank.application.impl.PropostaAceitaListener;
import com.funck.digitalbank.application.impl.PropostaContaEventPublisherDefault;
import com.funck.digitalbank.domain.model.Endereco;
import com.funck.digitalbank.domain.model.EtapaCriacaoProposta;
import com.funck.digitalbank.domain.model.FotoCPF;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.model.StatusProposta;
import com.funck.digitalbank.domain.repositories.PropostaContaRepository;
import com.funck.digitalbank.infrastructure.integration.ValidaDocumentoPessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class PropostaAceitaListenerTest {

    @InjectMocks
    private PropostaAceitaListener propostaAceitaListener;

    @Mock
    private ValidaDocumentoPessoa validaDocumentoPessoa;

    @Mock
    private PropostaContaRepository propostaContaRepository;

    @Mock
    private CadastroConta cadastroConta;

    @Captor
    private ArgumentCaptor<PropostaConta> propostaCaptor;

    private PropostaAceitaEvent propostaAceitaEvent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        PropostaConta proposta = getPropostaConta();

        this.propostaAceitaEvent = new PropostaAceitaEvent(PropostaContaEventPublisherDefault.class, proposta);
    }

    @Test
    @DisplayName("Deve lançar PropostaContaInvalidaException caso a proposta não esteja finalizada")
    public void testProcederProposta() {
        propostaAceitaEvent.getPropostaConta().setEtapaProposta(EtapaCriacaoProposta.ENDERECO_CADASTRADO);

        assertThrows(
                IllegalArgumentException.class,
                () -> propostaAceitaListener.procederProposta(propostaAceitaEvent)
        );
    }

    @Test
    @DisplayName("Deve mudar status da propostada para liberada quando o serviço externo validar a foto do cpf")
    public void testProcederProposta2() {
        // given
        doReturn(true).when(validaDocumentoPessoa).isValido(any(FotoCPF.class));

        // when
        propostaAceitaListener.procederProposta(propostaAceitaEvent);

        // then
        verify(propostaContaRepository).save(propostaCaptor.capture());

        assertEquals(StatusProposta.LIBERADA, propostaCaptor.getValue().getStatusProposta());
    }

    @Test
    @DisplayName("Deve mudar status da propostada para pendente quando o serviço externo não validar a foto do cpf")
    public void testProcederProposta3() {
        // given
        doReturn(false).when(validaDocumentoPessoa).isValido(any(FotoCPF.class));

        // when
        propostaAceitaListener.procederProposta(propostaAceitaEvent);

        // then
        verify(propostaContaRepository).save(propostaCaptor.capture());

        assertEquals(StatusProposta.PENDENTE, propostaCaptor.getValue().getStatusProposta());
    }

    @Test
    @DisplayName("Deve iniciar o processo de criar uma nova conta quando o dodcumento for validade e a proposta liberada")
    public void testProcederProposta4() {
        // given
        doReturn(true).when(validaDocumentoPessoa).isValido(any(FotoCPF.class));

        // when
        propostaAceitaListener.procederProposta(propostaAceitaEvent);

        // then
        verify(cadastroConta).criar(propostaAceitaEvent.getPropostaConta());
    }

    private PropostaConta getPropostaConta() {
        var pessoa = Pessoa.builder().endereco(new Endereco()).fotoCpf(new FotoCPF()).build();
        var proposta = new PropostaConta();
        proposta.setId("idProposta");
        proposta.setPessoa(pessoa);
        proposta.setEtapaProposta(EtapaCriacaoProposta.PROPOSTA_FINALIZADA);
        return proposta;
    }

}
