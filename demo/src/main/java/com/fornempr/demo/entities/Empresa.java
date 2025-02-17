package com.fornempr.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "empresa")
@Data
public class Empresa {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="CNPJ", length=14, nullable=false)
    String CNPJ;
    @Column(name="nome_fantasia", nullable=false)
    String nomeFantasia;
    @Column(name="CEP", nullable=false)
    Integer CEP;
}
