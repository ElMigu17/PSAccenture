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
    public Integer CEP;
    public List<Integer> fornecedores;
}
