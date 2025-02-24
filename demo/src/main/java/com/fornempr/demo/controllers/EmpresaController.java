package com.fornempr.demo.controllers;

import com.fornempr.demo.DTOs.EmpresaDto;
import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.repositories.EmpresaRepository;
import com.fornempr.demo.services.EmpresaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
public class EmpresaController {

    private final EmpresaService empresaService;


    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/empresas")
    public List<EmpresaDto> findAllEmpresa() {
        return this.empresaService.findAllEmpresa();
    }

    @PostMapping("/empresas")
    public Empresa addOneEmpresa(@RequestBody EmpresaDto empresaDto) {
        return this.empresaService.addOneEmpresa(empresaDto);
    }

    @DeleteMapping("/empresas/{id}")
    public void deleteEmpresa(@PathVariable Integer id) {
        this.empresaService.deleteById(id);
    }

    @PutMapping("/empresas")
    public Empresa editEmpresa(@RequestBody EmpresaDto empresaDto) {
        return this.empresaService.addOneEmpresa(empresaDto);
    }
}
