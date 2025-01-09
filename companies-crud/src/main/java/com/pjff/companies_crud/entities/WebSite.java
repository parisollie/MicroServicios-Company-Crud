package com.pjff.companies_crud.entities;

import jakarta.persistence.*;

import lombok.Data;

import java.io.Serializable;

//Vid 14
@Entity
@Data
public class WebSite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "category")
    //Para que lo mape en un string
    @Enumerated(value = EnumType.STRING)
    private Category category;
    private String description;
}

