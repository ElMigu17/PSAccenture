package com.fornempr.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmpresaDto {
    public Integer id;
    public String CNPJ;
    public String nomeFantasia;
    public String CEP;
    public String estado;
    public String listagemFornecedores;
    public List<Integer> fornecedores;
}
