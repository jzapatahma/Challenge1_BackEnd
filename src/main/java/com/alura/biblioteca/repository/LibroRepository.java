package com.alura.biblioteca.repository;

import com.alura.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
