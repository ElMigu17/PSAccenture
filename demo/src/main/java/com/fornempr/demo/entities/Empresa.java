package com.fornempr.demo.entities;

import com.fornempr.demo.DTOs.EmpresaDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "empresa")
@Data
public class Empresa {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="CNPJ", length=14, nullable=false)
    private String CNPJ;
    @Column(name="nome_fantasia", nullable=false)
    private String nomeFantasia;
    @Column(name="CEP", nullable=false)
    private Integer CEP;
    @ManyToMany()
    private List<Fornecedor> fornecedores;

    public Empresa(EmpresaDto empresaDto){
        this.id = empresaDto.id;
        this.CNPJ = empresaDto.CNPJ;
        this.nomeFantasia = empresaDto.nomeFantasia;
        this.CEP = empresaDto.CEP;

    }
}
