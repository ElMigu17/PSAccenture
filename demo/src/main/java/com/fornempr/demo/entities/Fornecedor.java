package com.fornempr.demo.entities;

import com.fornempr.demo.DTOs.EmpresaDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "fornecedor")
@Data
public class Fornecedor {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="cnpj", length=14, nullable=false)
    private String CNPJ;
    @Column(name="nome", nullable=false)
    private String nome;
    @Column(name="email", nullable=false)
    private String email;
    @Column(name="cep", nullable=false)
    private Integer CEP;


    @Column(name="cpf", nullable=false)
    private Integer CPF;
    @Column(name="rg", nullable=false)
    private String rg;
    @Column(name="data_de_nascimento", nullable=false)
    private Integer data_de_nascimento;
    @Column(name="is_pessoa_fisica", nullable=false)
    private Integer is_pessoa_fisica;

    @ManyToMany()
    private List<Empresa> empresas;

}
