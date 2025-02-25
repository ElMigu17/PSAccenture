package com.fornempr.demo.repositories;

import com.fornempr.demo.entities.Empresa;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends CrudRepository<Empresa, Integer> {

    @Query("SELECT e FROM Empresa as e " +
            "where e.CNPJ = :cnpj" )
    List<Empresa> findByCNPJ(String cnpj);

}
