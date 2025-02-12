package com.pjff.companies_crud.entities;

import jakarta.persistence.*;

import lombok.Data;

import java.io.Serializable;

//V-14,paso 5 ,mapeamos website de la base de datos.
@Entity
@Data
// Se serializa cuando la enviemos como un archivo Json
public class WebSite implements Serializable {

    //Mapeamos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "category")
    //Para que lo mape en un string para eso es el Enumerated
    @Enumerated(value = EnumType.STRING)
    //Creamos un archivo category
    private Category category;
    //Paso 7
    private String description;
}

