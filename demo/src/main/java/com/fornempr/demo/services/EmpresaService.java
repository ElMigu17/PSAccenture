package com.fornempr.demo.services;

import com.fornempr.demo.DTOs.EmpresaDto;
import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.repositories.EmpresaRepository;
import com.fornempr.demo.repositories.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmpresaService {

    private EmpresaRepository empresaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final long adulthoodInMilisecond = 568036800000L;
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
            empresaDtoList.add(new EmpresaDto(empresa.getId(), empresa.getCNPJ(), empresa.getNomeFantasia(), empresa.getCEP(), empresa.getEstado(), listagemFornecedores,fornecedorToEmpresaDtos));
        }
        return empresaDtoList;
    }

    public Empresa addOneEmpresa(EmpresaDto empresaDto) {
        Empresa empresa = new Empresa(empresaDto);
        if(empresaDto.getFornecedores() == null) {
            return this.empresaRepository.save(empresa);
        }
        List<Fornecedor> fornecedoresList = (List<Fornecedor>) fornecedorRepository.findAllById(empresaDto.getFornecedores());

        empresa.setFornecedor(fornecedoresList);
        String errors = this.validateFornecedor(empresa);
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(errors);
        }
        return this.empresaRepository.save(empresa);
    }

    private String validateFornecedor(Empresa empresa){
        String errors = "";

        if(this.checkIfCNPJIsUsed(empresa.getCNPJ(), empresa.getId())){
            errors += "Esse CNPJ já está sendo usado \n";
        }


        if(empresa.getEstado().equals("Paraná")){
            Date now = new Date();
            for(Fornecedor fornecedor : empresa.getFornecedor()){
                if(!fornecedor.getIs_pessoa_fisica()){
                    continue;
                }
                long ageInMilisecond = fornecedor.getData_nascimento().getTime() - now.getTime();
                if(adulthoodInMilisecond > ageInMilisecond){
                    errors += "Devido ao fato de a empresa ser do Paraná, ela não pode ter um fornecedor com menos de 18 anos \n";
                    break;
                }
            }
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
