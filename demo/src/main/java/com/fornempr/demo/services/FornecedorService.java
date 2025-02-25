package com.fornempr.demo.services;

import com.fornempr.demo.DTOs.FornecedorDto;
import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.repositories.EmpresaRepository;
import com.fornempr.demo.repositories.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FornecedorService {

    private EmpresaRepository empresaRepository;
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(EmpresaRepository empresaRepository, FornecedorRepository fornecedorRepository) {
        this.empresaRepository = empresaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<FornecedorDto> findAllFornecedor() {
        List<Fornecedor> fornecedores = (List<Fornecedor>) this.fornecedorRepository.findAll();
        List<FornecedorDto> fornecedorDtoList = new ArrayList<>();
        for(Fornecedor fornecedor : fornecedores){
            List<Integer> fornecedorToEmpresaDtos = new ArrayList<>();
            String listagemEmpresas = "";
            for (Empresa empresa : fornecedor.getEmpresa()){
                fornecedorToEmpresaDtos.add(empresa.getId());
                listagemEmpresas += empresa.getNomeFantasia() + " - " + empresa.getCNPJ() + " ,";
            }
            listagemEmpresas = listagemEmpresas != "" ?listagemEmpresas.substring(0, listagemEmpresas.length() - 1) : "";
            fornecedorDtoList.add(new FornecedorDto(fornecedor.getId(), fornecedor.getCNPJ(), fornecedor.getCPF(),
                    fornecedor.getNome(), fornecedor.getEmail(), fornecedor.getCEP(), fornecedor.getIs_pessoa_fisica(),
                    fornecedor.getRg(), fornecedor.getData_nascimento(), listagemEmpresas, fornecedorToEmpresaDtos));
        }
        return fornecedorDtoList;
    }

    public Fornecedor addOneFornecedor(FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = new Fornecedor(fornecedorDto);
        if(fornecedorDto.getEmpresas() == null) {
            return this.fornecedorRepository.save(fornecedor);
        }
        List<Empresa> empresaList = (List<Empresa>) empresaRepository.findAllById(fornecedorDto.getEmpresas());
        fornecedor.setEmpresa(empresaList);
        return this.fornecedorRepository.save(fornecedor);
    }

    public void deleteById(Integer id) {
        this.fornecedorRepository.deleteById(id);
    }

}
