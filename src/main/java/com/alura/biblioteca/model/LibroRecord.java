package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroRecord(
        @JsonAlias("id")                Integer idInterno,
        @JsonAlias("title")             String titulo,
        @JsonAlias("copyright")         Boolean derechosAutor,
        @JsonAlias("download_count")    Integer contadorDescargas,
        @JsonAlias("authors") List<AutorRecord> autores,
        @JsonAlias("languages")         List<String> lenguajes
) { }
