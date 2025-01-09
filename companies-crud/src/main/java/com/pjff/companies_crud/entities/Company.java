package com.pjff.companies_crud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

//V-13, mapeamos de la base de datos.
// Le decimos que es una entidad
@Entity
//Anotacion nos crea el getters and setters
@Data
public class Company {

    @Id
    //es un autoincrementable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String founder;
    private String logo;
    //Formato para fecha
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate foundationDate;
    //Vid 15
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "id_company", referencedColumnName = "id")
    //creamos al relacion
    private List<WebSite> webSites;
}

