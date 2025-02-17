package com.fornempr.demo.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "empresa")
@Data
public class Empresa {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    
    String CNPJ;
    String nomeFantasia;
    Integer CEP;
}
