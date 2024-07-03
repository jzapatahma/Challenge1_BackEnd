package com.alura.biblioteca.principal;
//
import com.alura.biblioteca.model.*;
import com.alura.biblioteca.repository.AutorRepository;
import com.alura.biblioteca.repository.BibliotecaRepository;
import com.alura.biblioteca.repository.LibroRepository;
import com.alura.biblioteca.servicios.ConsumirAPI;
import com.alura.biblioteca.servicios.ConvertirDatos;
import java.util.*;
import java.util.stream.Collectors;

//
// Listar toda la biblioteca https://gutendex.com/books/
// Buscar por titulo del libro y derecho de autor https://gutendex.com/books/?search=dickens%20great&copyright=true
// Buscar por año de inicio y lenguaje de escritura https://gutendex.com/books/?author_year_start=1900&languages=en,fr
// Buscar por autores antes del a.C vivos https://gutendex.com/books/?author_year_end=-499
// Buscar autores entre su año de nacimiento y defunsion /books?author_year_start=1800&author_year_end=1899
// Buscar que tengan derechos de autor https://gutendex.com/books?copyright=true
// Buscar por un lenguaje o varios https://gutendex.com/books?languages=fr,fi
// Buscar por titulo y/o autor http://gutendex.com/books?search=dickens%20great
//** este ultimo caso  dickens = nombre de autor y great = titulo del libro
// Buscar por titulo del libro solamente http://gutendex.com/books/?search=%20great
// Buscar por nombre autor solamente http://gutendex.com/books/?search=dickens
//
public class Principal {

    private BibliotecaRepository bibliotecaRepository;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    //
    private static final String URL_BASE = "https://gutendex.com/";
    private static final String API_KEY = "Key";
    private ConsumirAPI consumirAPI = new ConsumirAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    //
    public Principal(BibliotecaRepository bibliotecaRepository, LibroRepository libroRepository, AutorRepository autorRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    //
    public void menu(){
        // Damos las opciones de eleccion al usuario.
        Scanner teclado = new Scanner(System.in);
        var opcion = -1;
        while (opcion != 0){
            System.out.println("\n");
            var menu = """
                        1 - Buscar Libro por Titulo
                        2 - Listar Libros Registrados
                        3 - Listar Autores Registrados
                        4 - Listar Autores Vivos en un Determinado Año
                        5 - Listar Libros por Idioma
                        6 - Listar Bibioteca en la API
                        7 - Guardar Biblioteca y Libros
                        8 - Listar Biblioteca y Libros en Base de Datos
                    
                        Busqueda en Base de Datos
                        9 - Busqueda
                        10 -Top 5 Libros mas Descargados
                        11 -Descargas mayores a 1500
                        12 -Descargas x mayores a x JPQL
                        13 -Busque  por Titulo JPQL
                        14 -Buque por Id Libro
                    
                        0 - Salir
                    """;
            System.out.println(menu);
            System.out.println("\n   ** Elija la Opcion a Realizar ** ");
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    buscarPorTituloLibro();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;

                case 9:
                    // En construccion
                    //buscarLibroPorTituloBD();
                    break;
                case 10:
                    // En construccion
                    //buscarTop5Libros();
                    break;
                case 11:
                    // En construccion
                    // con Queries Nativas
                    //buscarDescargasMayores1500();
                    break;
                case 12:
                    // En construccion
                    // con JPQL(Java Persistence Query Language)
                    //buscarDescargasMayoresJPQL();
                    break;
                case 13:
                    // En construccion
                    //buscarTituloJPQL();
                    break;
                case 14:
                    // En construccion
                    //buscarIdlibro();
                    break;
                case 0:
                    System.out.println("Es un placer servirle... hasta pronto.\n");
                    break;
                default:
                    System.out.println("Opcion No Valida, vuelve a intentarlo.");
                    break;
            }
        }
    }

    // Obtenemos los datos de la API y los mastramos en pantalla
    public BibliotecaRecord obtenerDatosAPI(String seccionUrl){
        var json = consumirAPI.obtenerDatos(URL_BASE+seccionUrl);
        var datos = conversor.obtenerDatos(json, BibliotecaRecord.class);
        return datos;
    }
    public void mostrarInfoAPI(BibliotecaRecord datos){
        System.out.println("\n*** Informacion Encontrada ***\n");
        System.out.println("Biblioteca: " + datos.nombre() + " Nro registros: " + datos.contador() + " Next: " + Optional.ofNullable(datos.link_siguiente()).orElse("Sin pagina siguiente") + " Previous: " + Optional.ofNullable(datos.link_anterior()).orElse("Sin pagina anterios") + "\n");
        for(LibroRecord lR : datos.libros()){
            System.out.println("Libro: #-" + lR.idInterno() + " " + lR.titulo() + " " + lR.derechosAutor() + " " + lR.contadorDescargas() + " " + lR.languages());
            for(AutorRecord aR : lR.autores()){
                System.out.println("   Autores: " + aR.nombre() + " " + aR.nacimiento() + " " + aR.fallecimiento());
            }
        }
    }
    //
    public  void buscarPorTituloLibro(){
        System.out.println("\nBuscar Libro por Titulo en la API\n");
        String tituloLibro =  "The"; // "Briar Rose";
        String seccionUrl = "books/?search=" + tituloLibro.replace(" ", "%20");
        var datos = obtenerDatosAPI(seccionUrl);
        //
        if(!datos.libros().isEmpty()){
            mostrarInfoAPI(datos);
            guardarActualizarBD(datos);
        } else {
            System.out.println("Nombre de libro no encontrado el servicio de la API - Biblioteca Gutendex");
        }
        //
    }
    //
    public void listarLibrosRegistrados(){
        System.out.println("\nListar Libros Registrados en la API\n");
        String seccionUrl = "books/?copyright=true";
        var datos = obtenerDatosAPI(seccionUrl);
        //
        if(!datos.libros().isEmpty()){
            mostrarInfoAPI(datos);
            //guardarActualizarBD(datos);
        } else {
            System.out.println("Nombre de libro no encontrado el servicio de la API - Biblioteca Gutendex");
        }
        //
    }
    //
    public void listarAutoresRegistrados(){
        System.out.println("\nListar Autores Registrados en la API\n");
        String seccionUrl = "books/?copyright=true";
        var datos = obtenerDatosAPI(seccionUrl);
        //
        datos.libros().stream()
                .flatMap(libroRecord -> libroRecord.autores().stream()
                        .map(autor -> new AbstractMap.SimpleEntry<>(autor, libroRecord))) // Asociar cada autor con su libro
                .distinct()
                .sorted(Comparator.comparing(entry -> entry.getKey().nombre())) // Ordenar por nombre de autor
                .forEach(entry -> {
                    AutorRecord autor = entry.getKey();
                    LibroRecord libro = entry.getValue();
                    System.out.println( "Libro: " + libro.idInterno() + " - " + libro.titulo() +
                            " --> Autor: " + autor.nombre() + " Nacimiento: " + autor.nacimiento() + " Fallecimiento: " + autor.fallecimiento());
                });
    }
    //
    public void listarAutoresVivos(){
        System.out.println("\nListar Autores Vivos en Determinado Año en la API\n");
        Scanner entrada = new Scanner(System.in);
        int year = entrada.nextInt();
        var nacimiento = year - 100; // le sumamos 100 que es un estimado de vida humana, para consultar entre el año ingresado y 100 años menos de ese valor
        String seccionUrl = "books/?author_year_start="+nacimiento+"&author_year_end="+year;
        var datos = obtenerDatosAPI(seccionUrl);
        List<AutorRecord> listaAutores = new ArrayList<>(datos.libros().stream()
                .flatMap(libroRecord -> libroRecord.autores().stream())
                .toList());
        //Eliminamos los registros con nacimiento y fallecimiento en nullos
        listaAutores.removeIf(autor -> (autor.nacimiento() == null && autor.fallecimiento() == null));
        //
        // Modificamos si alguno entre Nacimiento y Fallecimiento son nullos y le llevamos: Si es null Nacimiento = fallecimiento - 75 % de vida humana y al lado inverso.
        // TreeSet, nos permite eliminar los duplicados y ordenar la lista
        TreeSet<AutorRecord> autoresTreeSet = listaAutores.stream()
                .map(autor -> new AutorRecord(
                        autor.nombre(),
                        autor.nacimiento() == null ? (autor.fallecimiento() - 75) : autor.nacimiento(),
                        autor.fallecimiento() == null ? (autor.nacimiento() + 75) : autor.fallecimiento()))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AutorRecord::nombre))));
        //
        System.out.println("\nAutores vivos en el año: " + year + "\n");
        autoresTreeSet.stream()  // parece ser que funcion, trae solo aquellos nacidos entre esas fecha y que su muerte sea superior al año consultado por el usurio, aqui seria 75 años promedio que debe ser sumado para hallar el valor del año de nacimiento.
                .filter(autor -> (autor.nacimiento() >= nacimiento && (autor.nacimiento() <= year) && (autor.fallecimiento() >= year)   ))
                //.forEach(System.out::println);
                .forEach(autor -> System.out.println(autor + " Tiempo de Vida = " + (autor.fallecimiento() - autor.nacimiento())));
        System.out.println("\nFin de los resultados");
    }
    //
    public void listarLibrosPorIdiomas(){
        System.out.println("Ingresa uno o varios idiomas, separados por comas: es,fr,en u otros");
        Scanner idiomaBuscar = new Scanner(System.in);
        String idiomas = idiomaBuscar.nextLine();
        String seccionUrl = "books/?languages="+idiomas;
        var datos = obtenerDatosAPI(seccionUrl);
        //
        System.out.println("\nListar Libros en los Idioma(s): " + idiomas );
        if(!datos.libros().isEmpty()){
            mostrarInfoAPI(datos);
            //guardarActualizarBD(datos);
        } else {
            System.out.println("Nombre de libro no encontrado el servicio de la API - Biblioteca Gutendex");
        }
    }

    public void guardarActualizarBD(BibliotecaRecord datos){
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nDesea guardar algun libro en la base de datos?: Elija 1=Si o 2=No");
        int opcionGuardar = teclado.nextInt();
        if (opcionGuardar==1){
            System.out.println("\nPor favor ingresa el Nro del libro:");
            int nroLibro = teclado.nextInt();
            // Vefificamos que el IdLibro digitado por el usuario si esta en datos de la consulta a la API
            Optional<LibroRecord> libroEnAPI = datos.libros().stream()
                    .filter(libro -> libro.idInterno() == nroLibro)
                    .findFirst();
            if(libroEnAPI.isPresent()){
                // Ahora verificamos si esta en la base de datos.
                Libro libroEnBD = libroRepository.buscarPorIdLibroJ(nroLibro);
                Biblioteca biblioteca = new Biblioteca();
                if(libroEnBD != null){ // Esta opcion permite modificar las 3 entidaades, menos el atributo nombre de biblioteca porque es de tipo Unico.
                    // Solo se actualizan aquellos atributos de las entidades que sean modificadas,
                    System.out.println("\nSe actualizaran los atributos que presenten alguna modificacion en las 3 entidades\n");
                    biblioteca = libroEnBD.getBiblioteca();
                    biblioteca.setContadorBiblioteca(datos.contador());
                    biblioteca.setSiguienteBiblioteca(datos.link_siguiente() + "-B1");
                    biblioteca.setAnteriorBiblioteca(datos.link_anterior()+ "-B1");
                    // Actualizamos el libro
                    libroEnBD.setIdLibro(libroEnAPI.get().idInterno());
                    libroEnBD.setTituloLibro(libroEnAPI.get().titulo() + "-L1");
                    libroEnBD.setContadorDescargasLibro(libroEnAPI.get().contadorDescargas());
                    libroEnBD.setDerechosAutorLibro(libroEnAPI.get().derechosAutor());
                    // Actualizamos los autores
                    List<Autor> listaAutores = new ArrayList<>();
                    for (AutorRecord autorAPI : libroEnAPI.get().autores()){
                        Optional<Autor> autorBD = autorRepository.findByNombreAutor(autorAPI.nombre());
                        Autor autorNuevo = new Autor();
                        if(autorBD.isPresent()){
                            autorNuevo = autorBD.get();
                            autorNuevo.setNombreAutor(autorAPI.nombre() + "-A1x");
                            autorNuevo.setNacimientoAutor(autorAPI.nacimiento());
                            autorNuevo.setFallecimientoAutor(autorAPI.fallecimiento() + 3000);
                        } else {
                            autorNuevo.setNombreAutor(autorAPI.nombre() + "-A1-nuevo");
                            autorNuevo.setNacimientoAutor(autorAPI.nacimiento());
                            autorNuevo.setFallecimientoAutor(autorAPI.fallecimiento() + 3000);
                        }
                        listaAutores.add(autorNuevo);
                    }
                    libroEnBD.setAutores(listaAutores);
                    biblioteca.setLibros(List.of(libroEnBD)); // list.of nos permite castear la clase libro por una lista
                    bibliotecaRepository.save(biblioteca);
                } else {
                    System.out.println("\nLibro: " + nroLibro + " No se existe en la base datos\n");
                    biblioteca = bibliotecaRepository.findByNombreBiblioteca("Biblioteca-Gutendex");
                    if(biblioteca==null){ // Si es nula la biblioteca es nueva, por lo tanto el libro y sus autores igual
                        System.out.println("Biblioteca No existe, procedemos a guardar los registros de la biblioteca, su libro y los autores\n");
                        Biblioteca bNueva = new Biblioteca();
                        bNueva.setContadorBiblioteca(datos.contador());
                        bNueva.setSiguienteBiblioteca(datos.link_siguiente());
                        bNueva.setNombreBiblioteca("Biblioteca-Gutendex");
                        bNueva.setAnteriorBiblioteca(datos.link_anterior());
                        List<Libro> listaNuevoLibro = new ArrayList<>();
                        for(LibroRecord l : libroEnAPI.stream().toList()){
                            Libro libroNuevo = new Libro();
                            libroNuevo.setContadorDescargasLibro(l.contadorDescargas());
                            libroNuevo.setDerechosAutorLibro(l.derechosAutor());
                            libroNuevo.setIdLibro(l.idInterno()); libroNuevo.setTituloLibro(l.titulo());
                            List<Autor> listaAutores = new ArrayList<>();
                            for(AutorRecord a : l.autores()){
                                Autor autorNuevo = new Autor();
                                autorNuevo.setFallecimientoAutor(a.fallecimiento());
                                autorNuevo.setNacimientoAutor(a.nacimiento());
                                autorNuevo.setNombreAutor(a.nombre());
                                listaAutores.add(autorNuevo);
                            }
                            libroNuevo.setAutores(listaAutores);
                            listaNuevoLibro.add(libroNuevo);
                        }
                        bNueva.setLibros(listaNuevoLibro);
                        bibliotecaRepository.save(bNueva);
                    }else { // Si existe la biblioteca, se procede a ingresar el libro y sus autores.
                        System.out.println("\nBiblioteca Existe, procedemos a guardar los registros su libro y los autores\n");
                        List<Libro> listaNuevoLibro = new ArrayList<>();
                        for(LibroRecord l : libroEnAPI.stream().toList()){
                            Libro libroNuevo = new Libro();
                            libroNuevo.setContadorDescargasLibro(l.contadorDescargas());
                            libroNuevo.setDerechosAutorLibro(l.derechosAutor());
                            libroNuevo.setIdLibro(l.idInterno()); libroNuevo.setTituloLibro(l.titulo());
                            List<Autor> listaAutores = new ArrayList<>();
                            for(AutorRecord a : l.autores()){
                                Autor autorNuevo = new Autor();
                                autorNuevo.setFallecimientoAutor(a.fallecimiento());
                                autorNuevo.setNacimientoAutor(a.nacimiento());
                                autorNuevo.setNombreAutor(a.nombre());
                                listaAutores.add(autorNuevo);
                            }
                            libroNuevo.setAutores(listaAutores);
                            listaNuevoLibro.add(libroNuevo);
                        }
                        biblioteca.setLibros(listaNuevoLibro);
                        bibliotecaRepository.save(biblioteca);
                    }
                }
            } else {
                System.out.println("Nro de libro no se encuentra en la consulta, verifique el listado en pantalla, e intentelo de nuevo");
            }
        }
    }

}
