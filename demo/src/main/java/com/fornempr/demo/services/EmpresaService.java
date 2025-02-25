package com.fornempr.demo.services;

import com.fornempr.demo.DTOs.EmpresaDto;
import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.repositories.EmpresaRepository;
import com.fornempr.demo.repositories.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmpresaService {

    private EmpresaRepository empresaRepository;
    private final FornecedorRepository fornecedorRepository;

    public EmpresaService(EmpresaRepository empresaRepository, FornecedorRepository fornecedorRepository) {
        this.empresaRepository = empresaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<EmpresaDto> findAllEmpresa() {
        List<Empresa> empresas = (List<Empresa>) this.empresaRepository.findAll();
        List<EmpresaDto> empresaDtoList = new ArrayList<>();
        for(Empresa empresa : empresas){
            List<Integer> fornecedorToEmpresaDtos = new ArrayList<>();
            String listagemFornecedores = "";
            for (Fornecedor fornecedor : empresa.getFornecedor()){
                fornecedorToEmpresaDtos.add(fornecedor.getId());
                String document = fornecedor.getIs_pessoa_fisica() ? fornecedor.getCPF().toString() : fornecedor.getCNPJ();
                listagemFornecedores += fornecedor.getNome() + " - " + document + " ,";
            }
            listagemFornecedores = listagemFornecedores!="" ?listagemFornecedores.substring(0, listagemFornecedores.length() - 1) : "";
            empresaDtoList.add(new EmpresaDto(empresa.getId(), empresa.getCNPJ(), empresa.getNomeFantasia(), empresa.getCEP(), listagemFornecedores,fornecedorToEmpresaDtos));
        }
        return empresaDtoList;
    }

    public Empresa addOneEmpresa(EmpresaDto empresaDto) {
        Empresa empresa = new Empresa(empresaDto);
        if(empresaDto.getFornecedores() == null) {
            return this.empresaRepository.save(empresa);
        }
        Iterable<Fornecedor> fornecedores = fornecedorRepository.findAllById(empresaDto.getFornecedores());
        List<Fornecedor> fornecedorList = StreamSupport
                .stream(fornecedores.spliterator(), false)
                .collect(Collectors.toList());
        empresa.setFornecedor(fornecedorList);
        return this.empresaRepository.save(empresa);
    }

    public void deleteById(Integer id) {
        this.empresaRepository.deleteById(id);
    }

}
