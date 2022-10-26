package com.serasaexperian.pessoa.service;


import com.serasaexperian.pessoa.dto.request.PessoaRequestDto;
import com.serasaexperian.pessoa.dto.response.PessoaResponseDto;
import com.serasaexperian.pessoa.model.AfinidadeModel;
import com.serasaexperian.pessoa.model.EstadoModel;
import com.serasaexperian.pessoa.model.PessoaModel;
import com.serasaexperian.pessoa.model.ScoreModel;
import com.serasaexperian.pessoa.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {
    @Mock
    private PessoaRepository repository;
    @Mock
    private ScoreService scoreService;
    @Mock
    private AfinidadeService afinidadeService;
    @InjectMocks
    private PessoaService pessoaService;

    private PessoaModel pessoaModel;
    private AfinidadeModel afinidadeModel;
    private EstadoModel estadoModel;
    private ScoreModel scoreModel;

    private UUID id = UUID.randomUUID();
    private LocalDateTime dataInclusao = LocalDateTime.now(ZoneId.of("UTC"));
    private String nome = "teste nome";
    private String telefone = "+55(11) 12345-6789";
    private Integer idade = 18;
    private String cidade = "São Paulo";
    private String estado = "São Paulo";
    private Integer score = 50;

    private String scoreDescricao = "teste score descrição";
    private Integer scoreInicial = 0;
    private Integer scoreFinal = 200;

    private String regiao = "Sudeste";
    private String sigla = "SP";
    private List<String> estados = Arrays.asList(sigla);

    @BeforeEach
    void setUp()
    {
        this.scoreModel = new ScoreModel();
        this.scoreModel.setId(this.id);
        this.scoreModel.setDescricao(this.scoreDescricao);
        this.scoreModel.setRangeInicial(this.scoreInicial);
        this.scoreModel.setRangeFinal(this.scoreFinal);

        this.estadoModel = new EstadoModel();
        this.estadoModel.setId(UUID.randomUUID());
        this.estadoModel.setSigla(this.sigla);

        this.afinidadeModel = new AfinidadeModel();
        this.afinidadeModel.setId(this.id);
        this.afinidadeModel.setRegiao(this.regiao);
        this.afinidadeModel.getEstados().add(this.estadoModel);

        this.pessoaModel = new PessoaModel();
        this.pessoaModel.setId(this.id);
        this.pessoaModel.setDataInclusao(this.dataInclusao);
        this.pessoaModel.setNome(this.nome);
        this.pessoaModel.setTelefone(this.telefone);
        this.pessoaModel.setIdade(this.idade);
        this.pessoaModel.setCidade(this.cidade);
        this.pessoaModel.setEstado(this.estado);
        this.pessoaModel.setScore(this.scoreModel);
        this.pessoaModel.setAfinidade(this.afinidadeModel);
    }

    @Test
    void saveSucesso() {
        when(repository.save(any(PessoaModel.class))).thenReturn(this.pessoaModel);
        when(scoreService.findOneByScoreRange(anyInt())).thenReturn(Optional.of(this.scoreModel));
        when(afinidadeService.findOneByRegiao(anyString())).thenReturn(Optional.of(this.afinidadeModel));

        PessoaModel pessoaModel = pessoaService.save(PessoaRequestDto.builder()
                        .nome(this.nome)
                        .telefone(this.telefone)
                        .idade(this.idade)
                        .cidade(this.cidade)
                        .estado(this.estado)
                        .score(this.score)
                        .regiao(this.regiao)
                        .build());

        assertEquals(pessoaModel.getId(), this.id);
        assertEquals(pessoaModel.getDataInclusao(), this.dataInclusao);
        assertEquals(pessoaModel.getNome(), this.nome);
        assertEquals(pessoaModel.getTelefone(), this.telefone);
        assertEquals(pessoaModel.getIdade(), this.idade);
        assertEquals(pessoaModel.getCidade(), this.cidade);
        assertEquals(pessoaModel.getEstado(), this.estado);
        assertEquals(pessoaModel.getScore().getId(), this.scoreModel.getId());
        assertEquals(pessoaModel.getScore().getDescricao(), this.scoreModel.getDescricao());
        assertEquals(pessoaModel.getScore().getRangeInicial(), this.scoreModel.getRangeInicial());
        assertEquals(pessoaModel.getScore().getRangeFinal(), this.scoreModel.getRangeFinal());
        assertEquals(pessoaModel.getAfinidade().getId(), this.afinidadeModel.getId());
        assertEquals(pessoaModel.getAfinidade().getRegiao(), this.afinidadeModel.getRegiao());
        assertEquals(pessoaModel.getAfinidade().getEstados().size(), this.afinidadeModel.getEstados().size());
    }


    @Test
    void saveErrorScoreNaoCadastrado() {
        when(scoreService.findOneByScoreRange(anyInt())).thenReturn(Optional.empty());

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            pessoaService.save(PessoaRequestDto.builder()
                    .nome(this.nome)
                    .telefone(this.telefone)
                    .idade(this.idade)
                    .cidade(this.cidade)
                    .estado(this.estado)
                    .score(this.score)
                    .regiao(this.regiao)
                    .build());;
        });
        assertEquals(responseStatusException.getStatus(), HttpStatus.CONFLICT);
        assertEquals(responseStatusException.getReason(), "Score não cadastrado.");
    }

    @Test
    void saveErrorRegiaoNaoCadastrada() {
        when(scoreService.findOneByScoreRange(anyInt())).thenReturn(Optional.of(this.scoreModel));
        when(afinidadeService.findOneByRegiao(anyString())).thenReturn(Optional.empty());

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            pessoaService.save(PessoaRequestDto.builder()
                    .nome(this.nome)
                    .telefone(this.telefone)
                    .idade(this.idade)
                    .cidade(this.cidade)
                    .estado(this.estado)
                    .score(this.score)
                    .regiao(this.regiao)
                    .build());;
        });
        assertEquals(responseStatusException.getStatus(), HttpStatus.CONFLICT);
        assertEquals(responseStatusException.getReason(), "Região não cadastrada.");
    }

    @Test
    void findAllSucesso() {
        when(repository.findAll()).thenReturn(Arrays.asList(pessoaModel));

        List<PessoaResponseDto> pessoasResponseDto = pessoaService.findAll();

        assertFalse(pessoasResponseDto.isEmpty());
        assertEquals(pessoasResponseDto.get(0).getNome(), this.nome);
        assertEquals(pessoasResponseDto.get(0).getCidade(), this.cidade);
        assertEquals(pessoasResponseDto.get(0).getNome(), this.nome);
        assertEquals(pessoasResponseDto.get(0).getEstado(), this.estado);
        assertEquals(pessoasResponseDto.get(0).getScoreDescricao(), this.scoreDescricao);
        assertEquals(pessoasResponseDto.get(0).getEstados().size(), this.estados.size());
        assertNull(pessoasResponseDto.get(0).getTelefone());
        assertNull(pessoasResponseDto.get(0).getIdade());
    }

    @Test
    void findByIdSucesso() {
        when(repository.findById(any())).thenReturn(Optional.of(pessoaModel));

        Optional<PessoaResponseDto> pessoaResponseDtoOptional = pessoaService.findById(this.id);

        assertTrue(pessoaResponseDtoOptional.isPresent());
        assertEquals(pessoaResponseDtoOptional.get().getNome(), this.nome);
        assertEquals(pessoaResponseDtoOptional.get().getTelefone(), this.telefone);
        assertEquals(pessoaResponseDtoOptional.get().getNome(), this.nome);
        assertEquals(pessoaResponseDtoOptional.get().getIdade(), this.idade);
        assertEquals(pessoaResponseDtoOptional.get().getScoreDescricao(), this.scoreDescricao);
        assertEquals(pessoaResponseDtoOptional.get().getEstados().size(), this.estados.size());
        assertNull(pessoaResponseDtoOptional.get().getCidade());
        assertNull(pessoaResponseDtoOptional.get().getEstado());
    }

    @Test
    void findByIdEmpty() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Optional<PessoaResponseDto> pessoaResponseDtoOptional = pessoaService.findById(this.id);

        assertFalse(pessoaResponseDtoOptional.isPresent());
    }

}
