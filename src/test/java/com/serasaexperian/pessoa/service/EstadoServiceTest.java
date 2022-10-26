package com.serasaexperian.pessoa.service;


import com.serasaexperian.pessoa.model.EstadoModel;
import com.serasaexperian.pessoa.repository.EstadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EstadoServiceTest {
    @Mock
    private EstadoRepository repository;
    @InjectMocks
    private EstadoService estadoService;

    private EstadoModel estadoModel;

    private UUID id = UUID.randomUUID();
    private String sigla = "SP";

    @BeforeEach
    void setUp()
    {
        this.estadoModel = new EstadoModel();
        this.estadoModel.setId(this.id);
        this.estadoModel.setSigla(this.sigla);
    }

    @Test
    void findBySiglaSucesso() {
        when(repository.findOneBySigla(anyString())).thenReturn(Optional.of(this.estadoModel));

        Optional<EstadoModel> estadoModelOptional = estadoService.findOneBySigla(this.sigla);

        assertTrue(estadoModelOptional.isPresent());
        assertEquals(estadoModelOptional.get().getId(), this.id);
        assertEquals(estadoModelOptional.get().getSigla(), this.sigla);
    }

}
