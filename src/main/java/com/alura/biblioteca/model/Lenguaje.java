package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tblLenguaje")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lenguaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @JsonAlias("name")
    private String name;

    public Lenguaje(String name) {
        this.name = name;
    }

    public Lenguaje() {}

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {  //Necesario para vializar los datos en el Json al convertirlo
        return "Lenguaje: {" +
                "name='" + name + '\'' +
                '}';
    }
}
