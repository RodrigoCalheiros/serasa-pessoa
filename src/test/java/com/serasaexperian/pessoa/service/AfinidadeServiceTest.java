package com.serasaexperian.pessoa.service;


import com.serasaexperian.pessoa.dto.request.AfinidadeRequestDto;
import com.serasaexperian.pessoa.dto.response.AfinidadeResponseDto;
import com.serasaexperian.pessoa.models.AfinidadeModel;
import com.serasaexperian.pessoa.models.EstadoModel;
import com.serasaexperian.pessoa.repository.AfinidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class AfinidadeServiceTest {

    @Mock
    private AfinidadeRepository repository;

    @Mock
    private EstadoService estadoService;

    private AfinidadeService afinidadeService;

    private AfinidadeModel afinidadeModel;

    private EstadoModel estadoModel;

    private UUID id = UUID.randomUUID();
    private String regiao = "Sudeste";
    private List<String> estados = Arrays.asList("RJ", "SP");

    @BeforeEach
    void setUp()
    {
        this.afinidadeService = new AfinidadeService(this.repository, this.estadoService);

        this.afinidadeModel = new AfinidadeModel();
        this.afinidadeModel.setId(id);
        this.afinidadeModel.setRegiao(this.regiao);

        this.estadoModel = new EstadoModel();
        this.estadoModel.setId(UUID.randomUUID());
        this.estadoModel.setSigla("SP");
        this.afinidadeModel.getEstados().add(this.estadoModel);

    }

    @Test
    void saveSucesso() {
        when(repository.save(any(AfinidadeModel.class))).thenReturn(this.afinidadeModel);
        when(estadoService.findBySigla(anyString())).thenReturn(Optional.of(this.estadoModel));

        AfinidadeResponseDto afinidadeResponseDto = afinidadeService.save(AfinidadeRequestDto.builder().regiao(this.regiao).estados(this.estados).build());

        assertEquals(afinidadeResponseDto.getRegiao(), this.afinidadeModel.getRegiao());
        assertEquals(afinidadeResponseDto.getEstados().size(), this.afinidadeModel.getEstados().size());
    }

    @Test
    void saveErrorEstadoNaoCadastrado() {
        when(estadoService.findBySigla(anyString())).thenReturn(Optional.empty());

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            afinidadeService.save(AfinidadeRequestDto.builder().regiao(this.regiao).estados(this.estados).build());
        });
        assertEquals(responseStatusException.getStatus(), HttpStatus.CONFLICT);
        assertEquals(responseStatusException.getReason(), "Estado não cadastrado");
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
