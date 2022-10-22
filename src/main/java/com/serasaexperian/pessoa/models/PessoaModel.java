package com.serasaexperian.pessoa.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_pessoa")
@Getter
@Setter
public class PessoaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dataInclusao;

    @Column(nullable = false, length = 70)
    private String nome;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false, length = 30)
    private String cidade;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(nullable = false, length = 20)
    private String regiao;
}
