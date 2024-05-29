package com.alura.biblioteca.Prueba1;

import com.alura.biblioteca.repository.MaestroxRepository;
import java.util.*;

public class Principal3 {

    private List<Maestro> datosMaestros = new ArrayList<>();
    private MaestroxRepository repoMaestro;

    public Principal3(MaestroxRepository repository) {
        this.repoMaestro= repository;
    }

    public Principal3() {
    }

    public static void main(String[] args) {

        // Creación de maestro y detalles
        Maestro maestro = new Maestro();
        maestro.setNombre("Maestro 1");

        Detalle detalle1 = new Detalle();
        detalle1.setDescripcion("Detalle 1");

        Detalle detalle2 = new Detalle();
        detalle2.setDescripcion("Detalle 2");

        MaestroxRepository rep = null;
        Principal3 principal3 = new Principal3();
        principal3.repoMaestro.save(maestro);

    }

}
