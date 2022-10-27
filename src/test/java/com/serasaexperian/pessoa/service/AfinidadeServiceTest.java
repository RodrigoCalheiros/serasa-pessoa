package com.serasaexperian.pessoa.service;


import com.serasaexperian.pessoa.dto.request.AfinidadeRequestDto;
import com.serasaexperian.pessoa.dto.response.AfinidadeResponseDto;
import com.serasaexperian.pessoa.model.AfinidadeModel;
import com.serasaexperian.pessoa.model.EstadoModel;
import com.serasaexperian.pessoa.repository.AfinidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AfinidadeServiceTest {

    @Mock
    private AfinidadeRepository repository;
    @Mock
    private EstadoService estadoService;
    @InjectMocks
    private AfinidadeService afinidadeService;

    private AfinidadeModel afinidadeModel;

    private EstadoModel estadoModel;

    private UUID id = UUID.randomUUID();
    private String regiao = "Sudeste";
    private String sigla = "SP";
    private List<String> estados = Arrays.asList(sigla);

    @BeforeEach
    void setUp()
    {
        this.estadoModel = new EstadoModel();
        this.estadoModel.setId(UUID.randomUUID());
        this.estadoModel.setSigla(this.sigla);

        this.afinidadeModel = new AfinidadeModel();
        this.afinidadeModel.setId(this.id);
        this.afinidadeModel.setRegiao(this.regiao);
        this.afinidadeModel.getEstados().add(this.estadoModel);

    }

    @Test
    void saveSucesso() {
        when(repository.save(any(AfinidadeModel.class))).thenReturn(this.afinidadeModel);
        when(estadoService.findOneBySigla(anyString())).thenReturn(Optional.of(this.estadoModel));

        AfinidadeResponseDto afinidadeResponseDto = afinidadeService.save(AfinidadeRequestDto.builder().regiao(this.regiao).estados(this.estados).build());

        assertEquals(afinidadeResponseDto.getRegiao(), this.afinidadeModel.getRegiao());
        assertEquals(afinidadeResponseDto.getEstados().size(), this.afinidadeModel.getEstados().size());
    }

    @Test
    void saveErrorEstadoNaoCadastrado() {
        when(estadoService.findOneBySigla(anyString())).thenReturn(Optional.empty());
        AfinidadeRequestDto afinidadeRequestDto = AfinidadeRequestDto.builder()
                .regiao(this.regiao)
                .estados(this.estados)
                .build();
        try{
            afinidadeService.save(afinidadeRequestDto);
            fail("Excepted");
        } catch (ResponseStatusException responseStatusException){
            assertEquals(HttpStatus.CONFLICT, responseStatusException.getStatus());
            assertEquals(String.format("Estado '%s' não cadastrado", this.sigla), responseStatusException.getReason());
        }
    }

    @Test
    void findByRegiaoSucesso() {
        when(repository.findOneByRegiao(anyString())).thenReturn(Optional.of(this.afinidadeModel));

        Optional<AfinidadeModel> afinidadeModelOptional = afinidadeService.findOneByRegiao(this.regiao);

        assertTrue(afinidadeModelOptional.isPresent());
        assertEquals(afinidadeModelOptional.get().getRegiao(), this.afinidadeModel.getRegiao());
        assertEquals(afinidadeModelOptional.get().getEstados().size(), this.afinidadeModel.getEstados().size());
    }

    @Test
    void existsByRegiaoSucesso() {
        when(repository.existsByRegiao(anyString())).thenReturn(true);

        Boolean existsByRegiao = afinidadeService.existsByRegiao(this.regiao);

        assertTrue(existsByRegiao);
    }

}
