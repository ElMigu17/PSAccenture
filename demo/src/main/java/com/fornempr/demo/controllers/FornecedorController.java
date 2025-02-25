package com.fornempr.demo.controllers;

import com.fornempr.demo.DTOs.FornecedorDto;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.repositories.FornecedorRepository;
import com.fornempr.demo.services.FornecedorService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping("/fornecedor")
    public Iterable<FornecedorDto> findAllFornecedor() {
        return this.fornecedorService.findAllFornecedor();
    }

    @PostMapping("/fornecedor")
    public Fornecedor addOneFornecedor(@RequestBody FornecedorDto fornecedorDto) {
        return this.fornecedorService.addOneFornecedor(fornecedorDto);
    }

    @DeleteMapping("/fornecedor/{id}")
    public void deleteFornecedor(@PathVariable Integer id) {
        this.fornecedorService.deleteById(id);
    }

    @PutMapping("/fornecedor")
    public Fornecedor editFornecedor(@RequestBody FornecedorDto fornecedorDto) {
        return this.fornecedorService.addOneFornecedor(fornecedorDto);
    }
}
