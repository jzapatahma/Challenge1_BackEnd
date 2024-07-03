package com.alura.biblioteca.repository;

import com.alura.biblioteca.model.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {

    //Optional<Libro> finByTituloContaisIgnoreCase(String nombreLibro);
    //Optional<Biblioteca> findFirstByNombreBibliotecaContainsIgnoreCase(String nombreLibro);

    List<Biblioteca> findByNombreBibliotecaContainsIgnoreCase(String nombreBiblioteca);

    Biblioteca findByNombreBiblioteca(String nombreBiblioteca);


//    @Query(value = "SELECT b.*, l.*, a.nombreAutor as nombreautor " +
//            "FROM tbl_bibliotecas b JOIN tbl_libros l ON b.idbca = l.biblioteca_idbca " +
//            "JOIN tbl_autores a ON l.idro = a.libro_idlro" ,nativeQuery = true)
//    Biblioteca buscarPorNombre(String nombreBiblioteca);
//            //"WHERE tbl_bibliotecas.nombre LIKE %:nombreBiblioteca%", nativeQuery = true)

//    @Query(value = "select * from tbl_bibliotecas", nativeQuery = true)
//    Biblioteca buscarPorNombreX(String nombreBiblioteca);

//    // funciona bien siempre y cuando no los id de cada table no se llamen igual
//    @Query(value = "SELECT b.*, l.*, a.nombreAutor as nombreautor " +
//            "FROM tbl_bibliotecas b JOIN tbl_libros l ON b.idbca = l.biblioteca_idbca " +
//            "JOIN tbl_autores a ON l.idlro = a.libro_idlro" ,nativeQuery = true)
//    Biblioteca buscarPorIdLibro2(int idLibro);

//    @Query(value = "SELECT b.*, l.* " +
//                    "FROM tbl_bibliotecas b " +
//                    "JOIN tbl_libros l ON b.idbca = l.biblioteca_idbca " +
//                    "JOIN tbl_autores a ON l.idlro = a.libro_idlro " +
//                    "WHERE l.id_libro=:idLibro" ,nativeQuery = true)
//    Biblioteca buscarPorIdLibro(int idLibro);

    @Query(value = "SELECT b.*, l.*, a.* "  +
            "FROM tbl_bibliotecas b JOIN tbl_libros l ON b.idbca = l.biblioteca_idbca " +
            "JOIN tbl_autores a ON l.idlro = a.libro_idlro " +
            "WHERE l.id_libro = :idLibro", nativeQuery = true)
    List<Biblioteca> buscarPorIdLibro (int idLibro);

    @Query(value = "SELECT b.*, l.*, a.* "  +
            "FROM tbl_bibliotecas b JOIN tbl_libros l ON b.idbca = l.biblioteca_idbca " +
            "JOIN tbl_autores a ON l.idlro = a.libro_idlro " +
            "WHERE l.id_libro = :idLibro", nativeQuery = true)
    List<Biblioteca> buscarPorIdLibro2(int idLibro);

    @Query(value = "SELECT b.*, l.*, a.* "  +
            "FROM tbl_bibliotecas b JOIN tbl_libros l ON b.idbca = l.biblioteca_idbca " +
            "JOIN tbl_autores a ON l.idlro = a.libro_idlro " +
            "WHERE l.id_libro = :idLibro", nativeQuery = true)
    Biblioteca buscarPorIdLibroJ (int idLibro);

    //Biblioteca findByIdLibro(int idLibro);

}
