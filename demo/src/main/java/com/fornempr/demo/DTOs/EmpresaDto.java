package com.fornempr.demo.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class EmpresaDto {
    public Integer id;
    public String CNPJ;
    public String nomeFantasia;
    public Integer CEP;
    public List<Integer> idFornecedores;
}
