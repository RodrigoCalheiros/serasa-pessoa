package com.serasaexperian.pessoa.repositories;

import com.serasaexperian.pessoa.models.EstadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoModel, UUID> {

    EstadoModel findOneBySigla(final String sigla);
}
