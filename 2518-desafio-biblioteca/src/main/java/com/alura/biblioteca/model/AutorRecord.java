package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorRecord(@JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer a_nacimiento,
        @JsonAlias("death_year") Integer a_fallecimiento) {
    //Cuerpo del record vacio por el momento.
    //Este record es usadodo para cargar algunos datos de la API
}
