package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="tblBibliotecas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @JsonProperty("count")
    private String count;
    @JsonProperty("next")
    private String next;
    @JsonProperty("previous")
    private String previous;

    @OneToMany(mappedBy = "biblioteca", cascade = CascadeType.ALL)
    private List<Libro> libros;

    @Transient
    @JsonProperty("results")
    private List<Libro> results;

    // Constructor con parametros de entrada
    public Biblioteca(String count, String next, String previous, List<Libro> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    // Contructor vacio
    public Biblioteca() {
    }

    // Getters y Setters
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Libro> getResults() {
        return results;
    }

    public void setResults(List<Libro> results) {
        this.results = results;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        // La siguiente linea asigna el Id de Biblioteca a cada registro de Libro.
        libros.forEach(e ->e.setBiblioteca(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                "Id=" + Id +
                ", count='" + count + '\'' +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                '}';
    }
}
