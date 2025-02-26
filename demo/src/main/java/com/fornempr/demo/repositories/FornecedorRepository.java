package com.fornempr.demo.repositories;

import com.fornempr.demo.entities.Fornecedor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FornecedorRepository extends CrudRepository<Fornecedor, Integer> {

    @Query("SELECT f FROM Fornecedor as f " +
            "where f.CNPJ = :cnpj" )
    List<Fornecedor> findByCNPJ(String cnpj);

    @Query("SELECT f FROM Fornecedor as f " +
            "where f.CPF = :cpf" )
    List<Fornecedor> findByCPF(String cpf);
}
