package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblLibros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    //
    @JsonProperty("id")
    private Integer idInterno;
    @JsonProperty("title")
    private String title;
    @JsonProperty("copyright")
    private Boolean copyright;
    @JsonProperty("download_count")
    private Integer download_count;

    @ManyToOne
    //@JoinColumn(name = "biblioteca_id") Averiguar esta linea que funcion tiene o si reemplaza otra funcion de asignacion de id en tablas detalles.
    private Biblioteca biblioteca;

    @Transient
    @JsonProperty("authors")
    private List<Autor> authors;
//    @Transient
//    @JsonProperty("languages")
//    private List<String> languages;

    public Libro(Integer idInterno, String title, Boolean copyright, Integer download_count) {
        this.idInterno = idInterno;
        this.title = title;
//        this.authors = authors;
//        this.languages = languages;
        this.copyright = copyright;
        this.download_count = download_count;
    }

    public Libro() {
    }

    //
    public Integer getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(Integer idInterno) {
        this.idInterno = idInterno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Autor> getAuthors() {
        return authors;
    }
    public void setAuthors(List<Autor> authors) {
        this.authors = authors;
    }
//    //
//    public List<String> getLanguages() {
//        return languages;
//    }
//
//    public void setLanguages(List<String> languages) {
//        this.languages = languages;
//    }
    //
    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public String toString() {
        return "Libro{" +
                ", idInterno=" + idInterno +
                ", title='" + title + '\'' +
                ", copyright=" + copyright +
                ", download_count=" + download_count +
                '}';
    }
}
