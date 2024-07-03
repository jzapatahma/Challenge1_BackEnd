package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BibliotecaRecord(
        String nombre,
        @JsonAlias("count") String contador,
        @JsonAlias("next")String link_siguiente,
        @JsonAlias("previous")String link_anterior,
        @JsonAlias("results") List<LibroRecord> libros
) {
}
