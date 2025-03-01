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
    private final long adulthoodInMilisecond = 568036800000L;

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

        String errors = this.validateFornecedor(fornecedor);
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(errors);
        }

        if(fornecedorDto.getEmpresas() == null || fornecedorDto.getEmpresas().isEmpty()) {
            return this.fornecedorRepository.save(fornecedor);
        }
        List<Empresa> empresaList = (List<Empresa>) empresaRepository.findAllById(fornecedorDto.getEmpresas());
        for(Empresa empresa : empresaList){
            empresa.getFornecedor().add(fornecedor);
        }

        this.fornecedorRepository.save(fornecedor);
        this.empresaRepository.saveAll(empresaList);
        return fornecedor;
    }

    public Fornecedor updateFornecedor(FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = new Fornecedor(fornecedorDto);
        String errors = this.validateFornecedor(fornecedor);
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(errors);
        }
        List<Empresa> empresas = (List<Empresa>) this.empresaRepository.findAllById(fornecedorDto.getEmpresas());
        fornecedor.setEmpresa(empresas);
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

        List<Empresa> empresasToSave = new ArrayList<>();
        empresasToSave.addAll(empresasToAdd);
        empresasToSave.addAll(empresasToRemove);

        this.empresaRepository.saveAll(empresasToSave);
         return fornecedor;
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

    private String validateFornecedor(Fornecedor fornecedor){
        String errors = "";
        if(this.checkCpfIsBeingDuplicated(fornecedor.getCPF(), fornecedor.getId())){
            errors += "Esse CPF já está sendo usado \n";

        }

        if(this.checkIfCNPJIsUsed(fornecedor.getCNPJ(), fornecedor.getId())){
            errors += "Esse CNPJ já está sendo usado \n";
        }

        if(fornecedor.getIs_pessoa_fisica()) {
            Date now = new Date();
            long ageInMilisecond = fornecedor.getData_nascimento().getTime() - now.getTime();
            if (adulthoodInMilisecond > ageInMilisecond) {
                for(Empresa empresa : fornecedor.getEmpresa()){
                    if(empresa.getEstado().equals("Paraná")){
                        errors += "Devido ao fato de a empresa ser do Paraná, ela não pode ter um fornecedor com menos de 18 anos \n";
                        break;
                    }
                }
            }

        }



        return errors;
    }

    public boolean checkCpfIsBeingDuplicated(String cpf, Integer id){
        List<Fornecedor> fornecedores = this.fornecedorRepository.findByCPF(cpf);
        return !(fornecedores.isEmpty() ||
                fornecedores.get(0).getId() == id);
    }

    public boolean checkIfCNPJIsUsed(String cnpj, Integer id){
        List<Fornecedor> fornecedores = this.fornecedorRepository.findByCNPJ(cnpj);

        return !(this.empresaRepository.findByCNPJ(cnpj).isEmpty() &&
                (fornecedores.isEmpty() ||
                fornecedores.get(0).getId() == id));
    }

    public void deleteById(Integer id) {
        this.fornecedorRepository.deleteById(id);
    }



}
