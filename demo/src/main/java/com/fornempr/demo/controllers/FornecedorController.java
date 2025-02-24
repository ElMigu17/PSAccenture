package com.fornempr.demo.controllers;

import com.fornempr.demo.DTOs.FornecedorDto;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.repositories.FornecedorRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
public class FornecedorController {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorController(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @GetMapping("/fornecedor")
    public Iterable<Fornecedor> findAllFornecedor() {
        return this.fornecedorRepository.findAll();
    }

    @PostMapping("/fornecedors")
    public Fornecedor addOneFornecedor(@RequestBody FornecedorDto fornecedorDto) {
        return this.fornecedorRepository.save(new Fornecedor(fornecedorDto));
    }

    @DeleteMapping("/fornecedors/{id}")
    public void deleteFornecedor(@PathVariable Integer id) {
        this.fornecedorRepository.deleteById(id);
    }

    @PutMapping("/fornecedors")
    public Fornecedor editFornecedor(@RequestBody FornecedorDto fornecedorDto) {
        return this.fornecedorRepository.save(new Fornecedor(fornecedorDto));
    }
}
