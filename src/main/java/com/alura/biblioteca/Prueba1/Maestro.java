package com.alura.biblioteca.Prueba1;

import com.alura.biblioteca.model.Libro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "maestros")
public class Maestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "maestro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalle> detalles = new ArrayList<>(); // Inicializar la lista, para evitar el error de nulo;


    public Maestro(String nombre) {
        //this.id = id;
        this.nombre = nombre;
        //this.detalles = detalles;
    }
    public Maestro() {
        detalles = new ArrayList<>(); // Inicializa la lista en el constructor para evitar el nulo,
        // se puede usar esta o la que este private List<Detalle> detalles = new ArrayList<>();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Maestro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
