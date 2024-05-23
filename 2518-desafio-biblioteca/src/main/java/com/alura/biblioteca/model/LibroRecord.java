package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroRecord(
        @JsonAlias("results") Object titulo
        //@JsonAlias("subjects") String temas,
        //@JsonAlias("bookshelves") String estanterias,
        //@JsonAlias("languages") String idioma,
        //@JsonAlias("copyright") Boolean derechos,
        //@JsonAlias("download_count") Integer descargas
) {
    //Cuerpo del record vacio por el momento.
    //Este record es usadodo para cargar algunos datos de la API
}
