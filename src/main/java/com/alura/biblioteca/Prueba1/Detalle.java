package com.alura.biblioteca.Prueba1;

import com.alura.biblioteca.model.Biblioteca;
import jakarta.persistence.*;

@Entity
@Table(name = "detalles")
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne
    //@JoinColumn(name = "maestro_id")  // averiguar si en realidad sirve o no
    private Maestro maestro;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Maestro getMaestro() {
        return maestro;
    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    @Override
    public String toString() {
        return "Detalle{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", maestro=" + maestro +
                '}';
    }
}
