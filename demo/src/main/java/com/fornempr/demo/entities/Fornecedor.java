package com.fornempr.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fornempr.demo.DTOs.FornecedorDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "fornecedor")
@Data
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fornecedor_generator")
    @SequenceGenerator(name = "fornecedor_generator", sequenceName = "fornecedor_seq", allocationSize = 1)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="cnpj", length=14)
    private String CNPJ;
    @Column(name="nome", nullable=false)
    private String nome;
    @Column(name="email", nullable=false)
    private String email;
    @Column(name="cep", nullable=false)
    private Integer CEP;


    @Column(name="cpf")
    private String CPF;
    @Column(name="rg")
    private String rg;
    @Column(name="data_nascimento")
    private Date data_nascimento;
    @Column(name="is_pessoa_fisica", nullable=false)
    private Boolean is_pessoa_fisica;

    @ManyToMany(mappedBy = "fornecedor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<Empresa> empresa = new ArrayList<>();

    public Fornecedor(){}

    public Fornecedor(FornecedorDto fornecedorDto){
        this.id = fornecedorDto.id;
        this.CNPJ = fornecedorDto.CNPJ;
        this.nome = fornecedorDto.nome;
        this.email = fornecedorDto.email;
        this.CEP = fornecedorDto.CEP;
        this.CPF = fornecedorDto.CPF;
        this.rg = fornecedorDto.rg;
        this.data_nascimento = fornecedorDto.data_nascimento;
        this.is_pessoa_fisica = fornecedorDto.is_pessoa_fisica;

    }

}
