package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tblBibliotecas")
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Idbca;
    @Column(unique = true)
    private String nombreBiblioteca;
    private String contadorBiblioteca;
    private String siguienteBiblioteca;
    private String anteriorBiblioteca;
    //    @Transient
    @OneToMany(mappedBy = "biblioteca", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Biblioteca(BibliotecaRecord bR){
        this.nombreBiblioteca = "Biblioteca Gutendex"; // creamos este campo para hacerlo unico en JPA y evita que se guarde varias veces el mismo registro en la base de datos.
        this.contadorBiblioteca = bR.contador();
        this.siguienteBiblioteca = bR.link_siguiente();
        this.anteriorBiblioteca = bR.link_anterior();
        this.libros = bR.libros().stream()
                .map(libroRecord -> new Libro()).toList();
        //.collect(Collectors.toList());
    }
    // Constructor vacio
    public Biblioteca() {
    }

    public Long getIdbca() {
        return Idbca;
    }

    public void setIdbca(Long idbca) {
        Idbca = idbca;
    }

    public String getNombreBiblioteca() {
        return nombreBiblioteca;
    }

    public void setNombreBiblioteca(String nombreBiblioteca) {
        this.nombreBiblioteca = nombreBiblioteca;
    }

    public String getContadorBiblioteca() {
        return contadorBiblioteca;
    }

    public void setContadorBiblioteca(String contadorBiblioteca) {
        this.contadorBiblioteca = contadorBiblioteca;
    }

    public String getSiguienteBiblioteca() {
        return siguienteBiblioteca;
    }

    public void setSiguienteBiblioteca(String siguienteBiblioteca) {
        this.siguienteBiblioteca = siguienteBiblioteca;
    }

    public String getAnteriorBiblioteca() {
        return anteriorBiblioteca;
    }

    public void setAnteriorBiblioteca(String anteriorBiblioteca) {
        this.anteriorBiblioteca = anteriorBiblioteca;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setBiblioteca(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        return
                "  Idbca=" + Idbca +
                        ", nombreBiblioteca='" + nombreBiblioteca + '\'' +
                        ", contadorBiblioteca='" + contadorBiblioteca + '\'' +
                        ", siguienteBiblioteca='" + siguienteBiblioteca + '\'' +
                        ", anteriorBiblioteca='" + anteriorBiblioteca + '\'' +
                        ", libros=" + libros ;
    }
}