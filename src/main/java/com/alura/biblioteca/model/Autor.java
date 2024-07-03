package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tblAutores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Idaor;
    private String nombreAutor;
    private Integer nacimientoAutor;
    private Integer fallecimientoAutor;
    @ManyToOne
    //@JoinColumn(name = "libro_idlro")
    private Libro libro;
    //
    public Autor() {
    }
    public Autor(Long Id, AutorRecord ar) {
        this.nombreAutor = ar.nombre();
        this.nacimientoAutor = ar.nacimiento();
        this.fallecimientoAutor = ar.fallecimiento();
    }

    public Long getIdaor() {
        return Idaor;
    }

    public void setIdaor(Long idaor) {
        Idaor = idaor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getNacimientoAutor() {
        return nacimientoAutor;
    }

    public void setNacimientoAutor(Integer nacimientoAutor) {
        this.nacimientoAutor = nacimientoAutor;
    }

    public Integer getFallecimientoAutor() {
        return fallecimientoAutor;
    }

    public void setFallecimientoAutor(Integer fallecimientoAutor) {
        this.fallecimientoAutor = fallecimientoAutor;
    }

    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return  "  Idaor=" + Idaor +
                ", nombreAutor='" + nombreAutor + '\'' +
                ", nacimientoAutor=" + nacimientoAutor +
                ", fallecimientoAutor=" + fallecimientoAutor +
                ", libro=" + libro ;
    }
}