package com.fornempr.demo.services;

import com.fornempr.demo.DTOs.FornecedorDto;
import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.repositories.EmpresaRepository;
import com.fornempr.demo.repositories.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.*;
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
        for(Empresa empresa : empresaList){
            empresa.getFornecedor().add(fornecedor);
        }

        this.empresaRepository.saveAll(empresaList);
        return this.fornecedorRepository.save(fornecedor);
    }

    public Fornecedor updateFornecedor(FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = new Fornecedor(fornecedorDto);
        Fornecedor oldFornecedor = this.fornecedorRepository.findById(fornecedor.getId()).get();

        Set<Integer> empresasAdded = this.empresasInFornecedor1AndNotIn2(fornecedor, oldFornecedor);
        Set<Integer> empresasRemoved = this.empresasInFornecedor1AndNotIn2(oldFornecedor, fornecedor);

        List<Empresa> empresasToAdd = (List<Empresa>) this.empresaRepository.findAllById(empresasAdded);
        List<Empresa> empresasToRemove = (List<Empresa>) this.empresaRepository.findAllById(empresasRemoved);

        for(Empresa empresa : empresasToAdd){
            empresa.getFornecedor().add(fornecedor);
        }
        for(Empresa empresa : empresasToRemove){
            empresa.getFornecedor().remove(fornecedor);
        }

        List<Empresa> empresasToSave = empresasToAdd;
        empresasToSave.addAll(empresasToRemove);

        this.empresaRepository.saveAll(empresasToSave);
        return this.fornecedorRepository.save(fornecedor);
    }


    private Set<Integer> empresasInFornecedor1AndNotIn2(Fornecedor fornecedor, Fornecedor fornecedor2){
        Set<Integer> idEmpresas = fornecedor2.getEmpresa().stream().map( e -> e.getId()).collect(Collectors.toSet());
        Set<Integer> empresasNotInFornecedor = new HashSet<>();
        for (Empresa empresa : fornecedor.getEmpresa()){
            if(!idEmpresas.contains(empresa.getId())){
                empresasNotInFornecedor.add(empresa.getId());
            }
        }
        return empresasNotInFornecedor;

    }



    public void deleteById(Integer id) {
        this.fornecedorRepository.deleteById(id);
    }



}
