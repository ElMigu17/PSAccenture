package com.fornempr.demo.controllers;

import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.repositories.EmpresaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @GetMapping("/empresas")
    public Iterable<Empresa> findAllEmployees() {
        return this.empresaRepository.findAll();
    }

    @PostMapping("/empresas")
    public Empresa addOneEmployee(@RequestBody Empresa empresa) {
        return this.empresaRepository.save(empresa);
    }
}
