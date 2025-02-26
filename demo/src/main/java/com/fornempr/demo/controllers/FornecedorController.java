package com.fornempr.demo.controllers;

import com.fornempr.demo.DTOs.FornecedorDto;
import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.services.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity addOneFornecedor(@RequestBody FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = null;
        try {
            fornecedor = this.fornecedorService.addOneFornecedor(fornecedorDto);
        }
        catch (IllegalArgumentException exception){
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fornecedor, HttpStatus.OK);
    }

    @DeleteMapping("/fornecedor/{id}")
    public void deleteFornecedor(@PathVariable Integer id) {
        this.fornecedorService.deleteById(id);
    }

    @PutMapping("/fornecedor")
    public ResponseEntity editFornecedor(@RequestBody FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = null;
        try {
            fornecedor = this.fornecedorService.updateFornecedor(fornecedorDto);
        }
        catch (IllegalArgumentException exception){
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fornecedor, HttpStatus.OK);
    }
}
