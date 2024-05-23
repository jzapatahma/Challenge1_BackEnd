package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lenguaje {
    @JsonAlias("name")
    private String name;

    @Override
    public String toString() {  //Necesario para vializar los datos en el Json al convertirlo
        return "LenguajeX{" +
                "name='" + name + '\'' +
                '}';
    }
}
