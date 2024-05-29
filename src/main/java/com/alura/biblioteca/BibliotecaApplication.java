package com.alura.biblioteca;

import com.alura.biblioteca.principal.Principal;
import com.alura.biblioteca.repository.BibliotecaRepository;
import com.alura.biblioteca.repository.LibroRepository;
import com.alura.biblioteca.repository.MaestroxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {

	@Autowired
	private BibliotecaRepository repoBiblio;
	@Autowired
	private LibroRepository repoLibro;
	@Autowired
	private MaestroxRepository repoMestro;


	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Probar la nueva conexion
//		var consumoApi = new ConsumoAPIs();
//		var json = consumoApi.obtenerDApi("https://gutendex.com/books/");
//		var convertir = new ConvertirDatos();
//		Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
//		teca.getResults().forEach(System.out::println);

		Principal principal = new Principal(repoBiblio, repoLibro,repoMestro);
		principal.mostarMenu();

	}
}
