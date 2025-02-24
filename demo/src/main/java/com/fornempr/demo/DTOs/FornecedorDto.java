package com.fornempr.demo.DTOs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FornecedorDto {
    public Integer id;
    public String CNPJ;
    public Integer CPF;
    public String nome;
    public String email;
    public Integer CEP;
    public Boolean is_pessoa_fisica;
    public String rg;
    public Date data_nascimento;
    public List<Integer> idEmpresas;
}
