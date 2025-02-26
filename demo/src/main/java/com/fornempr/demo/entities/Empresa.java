package com.fornempr.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fornempr.demo.DTOs.EmpresaDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
@Data
public class Empresa {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_generator")
    @SequenceGenerator(name = "empresa_generator", sequenceName = "empresa_seq", allocationSize = 1)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="CNPJ", length=14, nullable=false)
    private String CNPJ;
    @Column(name="nome_fantasia", nullable=false)
    private String nomeFantasia;
    @Column(name="CEP", nullable=false)
    private Integer CEP;
    @Column(name="estado", nullable=false)
    private String estado;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Fornecedor> fornecedor = new ArrayList<>();

    public Empresa(EmpresaDto empresaDto){
        this.id = empresaDto.id;
        this.CNPJ = empresaDto.CNPJ;
        this.nomeFantasia = empresaDto.nomeFantasia;
        this.CEP = empresaDto.CEP;
        this.estado = empresaDto.estado;
    }

    public Empresa(){}

}
