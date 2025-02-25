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
        String errors = this.validateFornecedor(empresa);
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(errors);
        }
        if(empresaDto.getFornecedores() == null) {
            return this.empresaRepository.save(empresa);
        }
        List<Fornecedor> fornecedoresList = (List<Fornecedor>) fornecedorRepository.findAllById(empresaDto.getFornecedores());
        empresa.setFornecedor(fornecedoresList);
        return this.empresaRepository.save(empresa);
    }

    private String validateFornecedor(Empresa empresa){
        String errors = "";

        if(this.checkIfCNPJIsUsed(empresa.getCNPJ(), empresa.getId())){
            errors += "CNPJ already is being used \n";
        }

        return errors;
    }

    public boolean checkIfCNPJIsUsed(String cnpj, Integer id){
        List<Empresa> empresas = this.empresaRepository.findByCNPJ(cnpj);

        return !(this.fornecedorRepository.findByCNPJ(cnpj).isEmpty() &&
                (empresas.isEmpty() || empresas.get(0).getId() == id));
    }

    public void deleteById(Integer id) {
        this.empresaRepository.deleteById(id);
    }

}
