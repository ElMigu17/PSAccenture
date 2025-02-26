package com.fornempr.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class FornecedorDto {
    public Integer id;
    public String CNPJ;
    public String CPF;
    public String nome;
    public String email;
    public Integer CEP;
    public Boolean is_pessoa_fisica;
    public String rg;
    public Date data_nascimento;
    public String listagemEmpresas;
    public List<Integer> empresas;
}
