package com.serasaexperian.pessoa.repositories;

import com.serasaexperian.pessoa.models.ScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreModel, UUID> {
}
