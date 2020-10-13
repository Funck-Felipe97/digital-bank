package com.funck.digitalbank.application;

import com.funck.digitalbank.application.impl.NovaPropostaContaDefault;
import com.funck.digitalbank.domain.exceptions.PropostaContaInvalidaException;
import com.funck.digitalbank.domain.model.Endereco;
import com.funck.digitalbank.domain.model.EtapaCriacaoProposta;
import com.funck.digitalbank.domain.model.FotoCPF;
import com.funck.digitalbank.domain.model.Pessoa;
import com.funck.digitalbank.domain.model.PropostaConta;
import com.funck.digitalbank.domain.repositories.PessoaRepository;
import com.funck.digitalbank.domain.repositories.PropostaContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class NovaPropostaContaTest {

    @InjectMocks
    private NovaPropostaContaDefault novaPropostaConta;

    @Mock
    private PropostaContaRepository propostaContaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PropostaContaEventPublisher propostaContaEventPublisher;

    @Captor
    private ArgumentCaptor<PropostaConta> propostaArgumentCaptor;

    private Pessoa pessoa;
    private Endereco endereco;
    private FotoCPF fotoCPF;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        pessoa = Pessoa.builder().email("fake@gmail.com").nome("Felipe").sobrenome("Funck").cpf("000.000.000-00").build();

        endereco = Endereco.builder().bairro("Bucarein").cep("00000-000").cidade("Joinville").complemento("12").estado("SC").rua("America").build();

        fotoCPF = FotoCPF.builder().fotoFrente("https:urlfoto/frente").fotoVerso("https:urlfoto/verso").build();
    }

    @Test
    @DisplayName("Deve criar uma proposta para a pessoa informada")
    public void testCriarNovaProposta() {
        // given
        doAnswer(answer -> {
            Pessoa pessoaSalva = answer.getArgument(0);
            pessoaSalva.setId("1");
            return pessoaSalva;
        }).when(pessoaRepository).save(pessoa);

        // when
        novaPropostaConta.novaProposta(pessoa);

        // then
        verify(propostaContaRepository).save(propostaArgumentCaptor.capture());

        var proposta = propostaArgumentCaptor.getValue();

        assertAll("proposta", () -> {
            assertEquals(pessoa, proposta.getPessoa());
            assertEquals("1", proposta.getPessoa().getId());
            assertEquals(EtapaCriacaoProposta.PESSOA_CADASTRADA, proposta.getEtapaProposta());
        });
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementExpection quando a proposta não existir na base")
    public void testCadastrarEndereco() {
        assertThrows(
                NoSuchElementException.class,
                () -> novaPropostaConta.cadastrarEndereco("idPorposta", endereco)
        );
    }

    @Test
    @DisplayName("Deve lançar PropostaContaInvalidaException quando for salvar um endereço e a proposta não tiver os dados da pessoa cadastrada")
    public void testCadastrarEndereco2() {
        // given
        var proposta = new PropostaConta();
        proposta.setEtapaProposta(EtapaCriacaoProposta.PESSOA_CADASTRADA);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(
                PropostaContaInvalidaException.class,
                () -> novaPropostaConta.cadastrarEndereco("idProposta", endereco)
        );
    }

    @Test
    @DisplayName("Deve cadastrar o endereço quando as etapas anteriores forem validas")
    public void testCadastrarEndereco3() {
        // given
        var proposta = new PropostaConta();

        proposta.setId("idProposta");
        proposta.setEtapaProposta(EtapaCriacaoProposta.PESSOA_CADASTRADA);
        proposta.setPessoa(pessoa);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        doAnswer(answer -> {
            Pessoa pessoaSalva = answer.getArgument(0);
            pessoaSalva.getEndereco().setId("idEndereco");
            return pessoaSalva;
        }).when(pessoaRepository).save(pessoa);

        // when
        novaPropostaConta.cadastrarEndereco("idProposta", endereco);

        // then
        verify(propostaContaRepository).save(propostaArgumentCaptor.capture());

        var propostaSalva = propostaArgumentCaptor.getValue();

        assertAll("proposta", () -> {
            assertEquals(endereco, propostaSalva.getPessoa().getEndereco());
            assertEquals("idEndereco", propostaSalva.getPessoa().getEndereco().getId());
            assertEquals(EtapaCriacaoProposta.ENDERECO_CADASTRADO, propostaSalva.getEtapaProposta());
        });
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando for cadastrar um endereço e a pessoa da proposta já tiver endereço cadastrado")
    public void testCadastrarEndereco4() {
        // given
        var proposta = new PropostaConta();
        pessoa.setEndereco(new Endereco());

        proposta.setId("idProposta");
        proposta.setEtapaProposta(EtapaCriacaoProposta.PESSOA_CADASTRADA);
        proposta.setPessoa(pessoa);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(IllegalArgumentException.class, () -> novaPropostaConta.cadastrarEndereco("idProposta", endereco));
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementExpection quando a proposta não existir na base")
    public void testCadastrarFotoCPF() {
        assertThrows(
                NoSuchElementException.class,
                () -> novaPropostaConta.cadastrarFotoCPF("idPorposta", fotoCPF)
        );
    }

    @Test
    @DisplayName("Deve lançar PropostaContaInvalidaException quando for salvar um cpf e a proposta não tiver os dados da pessoa cadastrada")
    public void testCadastrarFotoCPF2() {
        // given
        var proposta = new PropostaConta();
        proposta.setEtapaProposta(EtapaCriacaoProposta.ENDERECO_CADASTRADO);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(
                PropostaContaInvalidaException.class,
                () -> novaPropostaConta.cadastrarFotoCPF("idProposta", fotoCPF)
        );
    }

    @Test
    @DisplayName("Deve lançar PropostaContaInvalidaException quando for salvar um cpf e a proposta não tiver os dados do endereço cadastrado")
    public void testCadastrarFotoCPF3() {
        // given
        var proposta = new PropostaConta();
        proposta.setEtapaProposta(EtapaCriacaoProposta.ENDERECO_CADASTRADO);
        proposta.setPessoa(pessoa);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(
                PropostaContaInvalidaException.class,
                () -> novaPropostaConta.cadastrarFotoCPF("idProposta", fotoCPF)
        );
    }

    @Test
    @DisplayName("Deve cadastrar a foto do CPF quando as etapas anteriores estiverem corretas")
    public void testCadastrarFotoCPF4() {
        // given
        var proposta = new PropostaConta();

        proposta.setId("idProposta");
        proposta.setEtapaProposta(EtapaCriacaoProposta.ENDERECO_CADASTRADO);
        pessoa.setEndereco(endereco);
        proposta.setPessoa(pessoa);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        doAnswer(answer -> {
            Pessoa pessoaSalva = answer.getArgument(0);
            pessoaSalva.getFotoCpf().setId("idFotoCPF");
            return pessoaSalva;
        }).when(pessoaRepository).save(pessoa);

        // when
        novaPropostaConta.cadastrarFotoCPF("idProposta", fotoCPF);

        // then
        verify(propostaContaRepository).save(propostaArgumentCaptor.capture());

        var propostaSalva = propostaArgumentCaptor.getValue();

        assertAll("proposta", () -> {
            assertEquals(fotoCPF, propostaSalva.getPessoa().getFotoCpf());
            assertEquals("idFotoCPF", propostaSalva.getPessoa().getFotoCpf().getId());
            assertEquals(EtapaCriacaoProposta.CPF_CADASTRADO, propostaSalva.getEtapaProposta());
        });
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando for cadastrar um CPF e a pessoa da proposta já tiver cpf cadastrado")
    public void testCadastrarFotoCPF5() {
        // given
        var proposta = new PropostaConta();
        pessoa.setEndereco(endereco);
        pessoa.setFotoCpf(new FotoCPF());

        proposta.setId("idProposta");
        proposta.setEtapaProposta(EtapaCriacaoProposta.ENDERECO_CADASTRADO);
        proposta.setPessoa(pessoa);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(IllegalArgumentException.class, () -> novaPropostaConta.cadastrarFotoCPF("idProposta", fotoCPF));
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementExpection quando a proposta não existir na base")
    public void testFinalizarProposta() {
        assertThrows(
                NoSuchElementException.class,
                () -> novaPropostaConta.finalizarProposta("idProposta", true)
        );
    }

    @Test
    @DisplayName("Deve lançar PropostaContaInvalidaException a pessoa da proposta não estiver cadastrada")
    public void testFinalizarProposta2() {
        // given
        var proposta = new PropostaConta();
        proposta.setEtapaProposta(EtapaCriacaoProposta.CPF_CADASTRADO);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(
                PropostaContaInvalidaException.class,
                () -> novaPropostaConta.finalizarProposta("idProposta", true)
        );
    }

    @Test
    @DisplayName("Deve lançar PropostaContaInvalidaException o endereço da proposta não estiver cadastrado")
    public void testFinalizarProposta3() {
        // given
        var proposta = new PropostaConta();
        proposta.setPessoa(pessoa);
        proposta.setEtapaProposta(EtapaCriacaoProposta.CPF_CADASTRADO);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(
                PropostaContaInvalidaException.class,
                () -> novaPropostaConta.finalizarProposta("idProposta", true)
        );
    }

    @Test
    @DisplayName("Deve lançar PropostaContaInvalidaException a foto do cpf não estiver cadastrada")
    public void testFinalizarProposta4() {
        // given
        var proposta = new PropostaConta();
        proposta.setPessoa(pessoa);
        proposta.getPessoa().setEndereco(endereco);
        proposta.setEtapaProposta(EtapaCriacaoProposta.CPF_CADASTRADO);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");

        // when then
        assertThrows(
                PropostaContaInvalidaException.class,
                () -> novaPropostaConta.finalizarProposta("idProposta", true)
        );
    }

    @Test
    @DisplayName("Deve publicar evento de proposta aceita quando a proposta for aceita")
    public void testFinalizarProposta5() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");
        proposta.setPessoa(pessoa);
        proposta.getPessoa().setEndereco(endereco);
        proposta.getPessoa().setFotoCpf(fotoCPF);
        proposta.setEtapaProposta(EtapaCriacaoProposta.CPF_CADASTRADO);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");
        doReturn(proposta).when(propostaContaRepository).save(proposta);

        // when
        novaPropostaConta.finalizarProposta("idProposta", true);

        // then
        verify(propostaContaEventPublisher).publishPropostaAceitaEvent(proposta);
    }

    @Test
    @DisplayName("Deve publicar evento de proposta recusada quando a proposta for recusada")
    public void testFinalizarProposta6() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");
        proposta.setPessoa(pessoa);
        proposta.getPessoa().setEndereco(endereco);
        proposta.getPessoa().setFotoCpf(fotoCPF);
        proposta.setEtapaProposta(EtapaCriacaoProposta.CPF_CADASTRADO);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");
        doReturn(proposta).when(propostaContaRepository).save(proposta);

        // when
        novaPropostaConta.finalizarProposta("idProposta", false);

        // then
        verify(propostaContaEventPublisher).publishPropostaRecusadaEvent(proposta);
    }

    @Test
    @DisplayName("Deve finalizar a proposta quando todas as etapas forem validas")
    public void testFinalizarProposta7() {
        // given
        var proposta = new PropostaConta();
        proposta.setId("idProposta");
        proposta.setPessoa(pessoa);
        proposta.getPessoa().setEndereco(endereco);
        proposta.getPessoa().setFotoCpf(fotoCPF);
        proposta.setEtapaProposta(EtapaCriacaoProposta.CPF_CADASTRADO);

        doReturn(Optional.of(proposta)).when(propostaContaRepository).findById("idProposta");
        doReturn(proposta).when(propostaContaRepository).save(proposta);

        // when
        final PropostaConta propostaSalva = novaPropostaConta.finalizarProposta("idProposta", true);

        // then
        assertAll("proposta", () -> {
            assertEquals(EtapaCriacaoProposta.PROPOSTA_FINALIZADA, propostaSalva.getEtapaProposta());
        });
    }

}
