package com.pjff.companies_crud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/*V-13, mapeamos de la base de datos.
 Le decimos que es una entidad*/
@Entity
//Con Data de lombok,nos crea el getters and setters.
@Data
public class Company {
    //Ponemos nuestras propiedades
    @Id
    //es un autoincrementable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String founder;
    private String logo;
    //Formato para fecha
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    /* siempre que las palabras sean iguales podemos hacer uso de esto
    foundationDate = foundation_date(esto viene de la base de datos)*/
    private LocalDate foundationDate;
    //Paso 9,relacion una a muchos,persist cuando se haga una actualizacion
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    //Paso 10, columna con la que se hace la unión
    @JoinColumn(name = "id_company", referencedColumnName = "id")
    //V-15,paso 8 creamos al relacion entre las tablas de bd
    private List<WebSite> webSites;
}

