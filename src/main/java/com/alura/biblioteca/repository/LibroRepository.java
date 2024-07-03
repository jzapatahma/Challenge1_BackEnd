package com.alura.biblioteca.repository;

import com.alura.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // La siguiente linea funciona siempre y cuando la consulta solo traiga un resultado si trae varios surge error NonUniqueResultException

    //Optional<Libro> findByIdLibro(Integer idLibro);

    Libro findByIdLibro(Integer idLibro);

//    //Libro findByIdLibro(Integer ideLibro);
//
//    // Trae el primer resultado que contenga el titulo o parte el.
//    Optional<Libro> findFirstByTituloLibroContainsIgnoreCase(String nombreLibro);
//
//    List<Libro> findTop5ByOrderByContadorDescargasDesc();
//
//    // Usando Queries Nativas
//    @Query(value = "Select * from tbl_libros as l where l.derechos_autor_libro = true and l.contador_descargas_libro >= 1500 ", nativeQuery = true)
//    List<Libro> libroConDescargasMayores1500();
//
//    // Usando JPQL (Java Persistence Query Language)
//    // Libro = a la Entidad creada en java, l.contadorDescargas = atributo de la clase libro que contiene el dato, igual que para derechos de autor.
//    @Query("Select l from Libro l where l.derechosAutorLibro = :derechos and l.contadorDescargasLibro >= :descargas ")
//    List<Libro> libroConDescargasMayoresJPQL(Boolean derechos, int descargas);
//
//    //Suerie Natica Select b.count, l.* From tbl_bibliotecas b Inner Join tbl_libros l on b.id = l.biblioteca_id  Where l.titulo Like '%the%'
//    @Query("Select b.siguiente_biblioteca,  l From Biblioteca b Join b.libros l Where l.tituloLibro ILIKE %:nombreLibro%")
//    List<Object[]> libroPorTituloJPQL(String nombreLibro);
//    //Map<String, List<DetalleFactura>> facturas = new HashMap<>();
//
//
//    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.biblioteca WHERE l.idlro = :idLibro")
//    List<Libro> findByIdLibroX(@Param("idLibro") Long idLibro);


    @Query(value = "SELECT b.*, l.*, a.* "  +
            "FROM tbl_bibliotecas b JOIN tbl_libros l ON b.idbca = l.biblioteca_idbca " +
            "JOIN tbl_autores a ON l.idlro = a.libro_idlro " +
            "WHERE l.id_libro = :idLibro", nativeQuery = true)
    Libro buscarPorIdLibroJ (int idLibro);

}
