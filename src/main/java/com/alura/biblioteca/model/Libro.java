package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="tblLibros")
public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Idlro;
    private Integer idLibro;
    private String tituloLibro;
    private Boolean derechosAutorLibro;
    //@Column(name="contadorDescargas") // se usa para ponerle otro nombre distinto en la base de daotos.
    private Integer contadorDescargasLibro;
    private String lenguajes;
    @ManyToOne
    //@JoinColumn(name = "biblioteca_idbca")
    private Biblioteca biblioteca;
    //    @Transient
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // cambie FechType.EAGER
    private List<Autor> autores;
    //
    public Libro(){}
    public Libro(Long Id, LibroRecord lr) {
        //this.Id = Id;
        this.idLibro = lr.idInterno();
        this.tituloLibro = lr.titulo();
        this.derechosAutorLibro = lr.derechosAutor();
        this.contadorDescargasLibro = lr.contadorDescargas();
        this.lenguajes = lr.lenguajes().toString();
    }

    public Long getIdlro() {
        return Idlro;
    }

    public void setIdlro(Long idlro) {
        Idlro = idlro;
    }

    public Integer getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public Boolean getDerechosAutorLibro() {
        return derechosAutorLibro;
    }

    public void setDerechosAutorLibro(Boolean derechosAutorLibro) {
        this.derechosAutorLibro = derechosAutorLibro;
    }

    public Integer getContadorDescargasLibro() {
        return contadorDescargasLibro;
    }

    public void setContadorDescargasLibro(Integer contadorDescargasLibro) {
        this.contadorDescargasLibro = contadorDescargasLibro;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a -> a.setLibro(this));
        this.autores = autores;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public String toString() {
        return
                "Idlro=" + Idlro +
                        ", idLibro=" + idLibro +
                        ", tituloLibro='" + tituloLibro + '\'' +
                        ", derechosAutorLibro=" + derechosAutorLibro +
                        ", contadorDescargasLibro=" + contadorDescargasLibro +
                        ", lenguajes='" + lenguajes + '\'' +
                        ", biblioteca=" + biblioteca +
                        ", autores=" + autores ;
    }
}