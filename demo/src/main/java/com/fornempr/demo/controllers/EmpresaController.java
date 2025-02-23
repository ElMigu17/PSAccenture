package com.fornempr.demo.controllers;

import com.fornempr.demo.DTOs.EmpresaDto;
import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.repositories.EmpresaRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @GetMapping("/empresas")
    public Iterable<Empresa> findAllEmpresa() {
        return this.empresaRepository.findAll();
    }

    @PostMapping("/empresas")
    public Empresa addOneEmpresa(@RequestBody EmpresaDto empresaDto) {
        return this.empresaRepository.save(new Empresa(empresaDto));
    }

    @DeleteMapping("/empresas/{id}")
    public void deleteEmpresa(@PathVariable Integer id) {
        this.empresaRepository.deleteById(id);
    }

    @PutMapping("/empresas")
    public Empresa editEmpresa(@RequestBody EmpresaDto empresaDto) {
        return this.empresaRepository.save(new Empresa(empresaDto));
    }
}
