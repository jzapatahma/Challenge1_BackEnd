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
    //
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
                    ** Bienvenido al Sistema Literatura Gutendex **
                    
                    Elija la opcion API Blibioteca Gutendex
                        1 - Buscar Libro por Titulo
                        2 - Listar Libros Registrados
                        3 - Listar Autores Registrados
                        4 - Listar Autores Vivos en un Determinado Año
                        5 - Listar Libros por Idioma
                    
                    Elija la opcion Registros Guardados en la Base de Datos
                        6 - Busqueda Libro por Titulo
                        7 - Listar Libros Registrados
                        8 - Listar Autores Registrados
                        9 - Listar Autores Vivos en un Determinado Año
                        10- Listar Libros por Idioma
                    
                    Otras opciones
                        11- Top 5 Libros mas Descargados
                        12- Descargas mayores a 1500
                        13- Descargas x mayores a x JPQL
                        14- Busque  por Titulo JPQL
                        15- Buque por Id Libro
                    
                        0 - Salir
                    """;
            System.out.println(menu);
            System.out.println("\n   ** Elija la Opcion a Realizar ** ");
            //opcion = teclado.hasNextInt() ? Integer.parseInt(teclado.nextLine()) : -1;
            if(teclado.hasNextInt()){
                opcion = teclado.nextInt();
                switch (opcion) {
                    case 1:
                        buscarPorTituloLibroAPI();
                        break;
                    case 2:
                        listarLibrosRegistradosAPI();
                        break;
                    case 3:
                        listarAutoresRegistradosAPI();
                        break;
                    case 4:
                        listarAutoresVivosAPI();
                        break;
                    case 5:
                        listarLibrosPorIdiomasAPI();
                        break;
                    //
                    case 6:
                        buscarPorTituloLibroBD();
                        break;
                    case 7:
                        listarLibrosRegistradosBD();
                        break;
                    case 8:
                        listarAutoresRegistradosBD();
                        break;
                    case 9:
                        listarAutoresVivosBD();
                        break;
                    case 10:
                        listarLibrosPorIdiomasBD();
                        break;
                    //
                    case 11:
                        // con Queries Nativas
                        //buscarDescargasMayores1500();
                        break;
                    case 12:
                        // En construccion
                        // con JPQL(Java Persistence Query Language)
                        break;
                    case 13:
                        //buscarDescargasMayoresJPQL();
                        break;
                    case 14:
                        // En construccion
                        //buscarTituloJPQL();
                        break;
                    case 15:
                        //buscarTop5Libros();
                        break;
                    //
                    case 0:
                        System.out.println("Es un placer servirle... hasta pronto.\n");
                        break;
                    default:
                        System.out.println("Opcion No Valida, vuelve a intentarlo.");
                        presioneEnterParaContinuar(teclado);
                        break;
                }
                if(opcion!=0){
                    presioneEnterParaContinuar(teclado);
                }
            } else {
                System.out.println("Entrada de teclado no valida, intentalo de nuevo");
                presioneEnterParaContinuar(teclado);
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
            System.out.println("Libro: # " + lR.idInterno() + " " + lR.titulo() + " " + lR.derechosAutor() + " " + lR.contadorDescargas() + " " + lR.lenguajes());
            for(AutorRecord aR : lR.autores()){
                System.out.println("   Autores: " + aR.nombre() + " " + aR.nacimiento() + " " + aR.fallecimiento());
            }
        }
    }
    //
    public  void buscarPorTituloLibroAPI(){
        System.out.println("\nBuscar Libro por Titulo en la API\n");
        System.out.println("Por favor ingrese el titulo del libro a buscar:");
        Scanner teclado = new Scanner(System.in);
        String tituloLibro =  teclado.nextLine(); //"The"; // "Briar Rose";
        String seccionUrl = "books/?search=" + tituloLibro.replace(" ", "%20");
        var datos = obtenerDatosAPI(seccionUrl);
        if(datos != null){
            if(!datos.libros().isEmpty()){
                mostrarInfoAPI(datos);
                guardarActualizarBD(datos);
            } else {
                System.out.println("\nLibro con Titulo: " + tituloLibro + " No se Encuentra en la Biblioteca Gutendex API");
                //System.out.println("Presiona Enter para continuar...");
                //teclado.nextLine();
                //presioneEnterParaContinuar(teclado);
            }
        } else {
            System.out.println("\nSin informacion de: " + tituloLibro + " en Biblioteca Gutendex API");
//            System.out.println("Presiona Enter para continuar...");
//            teclado.nextLine();
            //presioneEnterParaContinuar(teclado);
        }
    }
    public  void buscarPorTituloLibroBD(){
        System.out.println("\nBuscar Libro por Titulo en la Base de Datos\n");
        System.out.println("Por favor ingrese el titulo del libro a buscar:");
        Scanner teclado = new Scanner(System.in);
        String tituloLibro =  teclado.nextLine(); //"The"; // "Briar Rose";
        Libro libroBD = libroRepository.findByTituloLibroContainsIgnoreCase(tituloLibro);
        if(libroBD!=null){
            Biblioteca biblioteca = libroBD.getBiblioteca();
            System.out.println("Biblioteca: " + biblioteca.getNombreBiblioteca());
            System.out.println("Nro de Registros: " + biblioteca.getContadorBiblioteca());
            System.out.println("Pagina Siguiente: " + biblioteca.getSiguienteBiblioteca());
            System.out.println("Pagina Anterior: " + biblioteca.getAnteriorBiblioteca());
            //
            for (Libro libro : List.of(libroBD)){
                System.out.println("***  Libros ***");
                System.out.println("-> Titulo: " + libro.getTituloLibro() + " Descargas " + libro.getContadorDescargasLibro() + " Copyrigth: " + libro.getDerechosAutorLibro());
                for (Autor autor : libro.getAutores()){
                    System.out.println("---> Autor: " + autor.getNombreAutor() + " Nacimiento: " + autor.getNacimientoAutor() + " Fallecimiento: " + autor.getFallecimientoAutor());
                }
            }
        }
    }
    //
    public void listarLibrosRegistradosAPI(){
        System.out.println("\nListando Libros Registrados en la API...\n");
        String seccionUrl = "books/?copyright=true";
        var datos = obtenerDatosAPI(seccionUrl);
        //
        if(!datos.libros().isEmpty()){
            mostrarInfoAPI(datos);
            guardarActualizarBD(datos);
        } else {
            System.out.println("Nombre de libro no encontrado el servicio de la API - Biblioteca Gutendex");
        }
    }
    public void listarLibrosRegistradosBD(){
        System.out.println("\nListando Registrados en la Base de Datos...\n");
        List<Libro> libroBD = libroRepository.findAll();
        if(!libroBD.isEmpty()){
            Biblioteca biblioteca = libroBD.get(0).getBiblioteca();
            System.out.println("\nBiblioteca: " + biblioteca.getNombreBiblioteca());
            System.out.println("Nro de Registros: " + biblioteca.getContadorBiblioteca());
            System.out.println("Pagina Siguiente: " + biblioteca.getSiguienteBiblioteca());
            System.out.println("Pagina Anterior: " + biblioteca.getAnteriorBiblioteca());
            //
            System.out.println("\n***  Libros ***");
            for (Libro libro : libroBD){
                System.out.println("-> Titulo: " + libro.getTituloLibro() + " Descargas: " + libro.getContadorDescargasLibro() + " Copyrigth: " + libro.getDerechosAutorLibro());
            }
        }
    }
    //
    public void listarAutoresRegistradosAPI(){
        System.out.println("\nListando Autores Registrados en la API...\n");
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
        //
        if(!datos.libros().isEmpty()){
            //mostrarInfoAPI(datos);
            guardarActualizarBD(datos);
        } else {
            System.out.println("Nombre de libro no encontrado el servicio de la API - Biblioteca Gutendex");
        }

    }
    public void listarAutoresRegistradosBD(){
        System.out.println("\nListando Autores Registrados en la Base de Datos...\n");
        List<Libro> libroBD  = libroRepository.findAll().stream().distinct().toList();
        if(!libroBD.isEmpty()){
            Biblioteca biblioteca = libroBD.get(0).getBiblioteca();
            System.out.println("Biblioteca: " + biblioteca.getNombreBiblioteca());
            System.out.println("Nro de Registros: " + biblioteca.getContadorBiblioteca());
            System.out.println("Pagina Siguiente: " + biblioteca.getSiguienteBiblioteca());
            System.out.println("Pagina Anterior: " + biblioteca.getAnteriorBiblioteca());
            //
            for (Libro libro : libroBD){
                for (Autor autor : libro.getAutores()){
                    System.out.println("---> Autor: " + autor.getNombreAutor() + " Nacimiento: " + autor.getNacimientoAutor() + " Fallecimiento: " + autor.getFallecimientoAutor());
                }
            }
        }
    }
    //
    public void listarAutoresVivosAPI(){
        System.out.println("\nListando Autores Vivos en Determinado Año en la API...\n");
        System.out.println("Por favor ingrese el año:");
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
                .filter(autor -> (autor.fallecimiento() > year && autor.nacimiento() <= year))
                .forEach(autor -> System.out.println(autor + " Tiempo de Vida = " + (autor.fallecimiento() - autor.nacimiento())));
        System.out.println("\nFin de los resultados");
    }
    public void listarAutoresVivosBD(){
        System.out.println("\nListando Autores Vivos Registrados en la Base de Datos...\n");
        System.out.println("Por favor ingrese el año");
        Scanner teclado = new Scanner(System.in);
        Integer year = teclado.nextInt();
        List<Autor> autorBD = libroRepository.findAll().stream()
                .flatMap(libro -> libro.getAutores().stream())  // flatMap to get a stream of autores from each libro
                .filter(autor -> ((autor.getFallecimientoAutor() > year && autor.getNacimientoAutor() <= year))) // filtra y trae solo autores nacidos antes o en ese mismo año y estando vivos en ese año de consulta.
                .distinct()  // Get distinct autores (assuming names are unique identifiers)
                .toList();
        if(!autorBD.isEmpty()){
            Biblioteca biblioteca = autorBD.get(0).getLibro().getBiblioteca();
            System.out.println("Biblioteca: " + biblioteca.getNombreBiblioteca());
            System.out.println("Nro de Registros: " + biblioteca.getContadorBiblioteca());
            System.out.println("Pagina Siguiente: " + biblioteca.getSiguienteBiblioteca());
            System.out.println("Pagina Anterior: " + biblioteca.getAnteriorBiblioteca());
            for (Autor autor : autorBD){
                System.out.println("---> Autor: " + autor.getNombreAutor() + " Nacimiento: " + autor.getNacimientoAutor() + " Fallecimiento: " + autor.getFallecimientoAutor());
            }
        }
    }
    //
    public void listarLibrosPorIdiomasAPI(){
        System.out.println("Ingresa uno o varios idiomas, separados por comas: es,fr,en u otros");
        Scanner idiomaBuscar = new Scanner(System.in);
        String idiomas = idiomaBuscar.nextLine();
        String seccionUrl = "books/?languages="+idiomas;
        System.out.println("\nListando Libros por Idioma(s): " + idiomas + " ...");
        var datos = obtenerDatosAPI(seccionUrl);
        //
        if(!datos.libros().isEmpty()){
            mostrarInfoAPI(datos);
            //guardarActualizarBD(datos);
        } else {
            System.out.println("Nombre de libro no encontrado el servicio de la API - Biblioteca Gutendex");
        }
    }
    public void listarLibrosPorIdiomasBD(){
        System.out.println("Ingresa uno o varios idiomas, separados por comas: es,fr,en u otros");
        Scanner teclado = new Scanner(System.in);
        String idiomas =  teclado.nextLine(); //"The"; // "Briar Rose";
        System.out.println("\nListando Libros por Idioma(s): " + idiomas + " ...");
        List<Libro> libroBD = libroRepository.findByLenguajesContainsIgnoreCase(idiomas);
        if(libroBD!=null){
            Biblioteca biblioteca = libroBD.get(0).getBiblioteca();
            System.out.println("Biblioteca: " + biblioteca.getNombreBiblioteca());
            System.out.println("Nro de Registros: " + biblioteca.getContadorBiblioteca());
            System.out.println("Pagina Siguiente: " + biblioteca.getSiguienteBiblioteca());
            System.out.println("Pagina Anterior: " + biblioteca.getAnteriorBiblioteca());
            //
            System.out.println("***  Libros ***");
            for (Libro libro : libroBD){
                System.out.println("-> Titulo: " + libro.getTituloLibro() + " Descargas " + libro.getContadorDescargasLibro() + " Copyrigth: " + libro.getDerechosAutorLibro() + " Lenguajes: " + libro.getLenguajes());
                for (Autor autor : libro.getAutores()){
                    System.out.println("---> Autor: " + autor.getNombreAutor() + " Nacimiento: " + autor.getNacimientoAutor() + " Fallecimiento: " + autor.getFallecimientoAutor());
                }
            }
        }
    }
    //
    public void guardarActualizarBD(BibliotecaRecord datos){
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nDesea guardar algun libro en la base de datos?: Elija 1=Si o 2=No");
        if(teclado.hasNextInt()){
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
                        //System.out.println("\nSe actualizaran los atributos que presenten alguna modificacion en las 3 entidades\n");
                        biblioteca = libroEnBD.getBiblioteca();
                        biblioteca.setContadorBiblioteca(datos.contador());
                        biblioteca.setSiguienteBiblioteca(datos.link_siguiente());
                        biblioteca.setAnteriorBiblioteca(datos.link_anterior());
                        // Actualizamos el libro
                        libroEnBD.setIdLibro(libroEnAPI.get().idInterno());
                        libroEnBD.setTituloLibro(libroEnAPI.get().titulo());
                        libroEnBD.setContadorDescargasLibro(libroEnAPI.get().contadorDescargas());
                        libroEnBD.setDerechosAutorLibro(libroEnAPI.get().derechosAutor());
                        libroEnBD.setLenguajes(libroEnAPI.get().lenguajes().toString());
                        // Actualizamos los autores
                        List<Autor> listaAutores = new ArrayList<>();
                        for (AutorRecord autorAPI : libroEnAPI.get().autores()){
                            Optional<Autor> autorBD = autorRepository.findByNombreAutor(autorAPI.nombre());
                            Autor autorNuevo = new Autor();
                            if(autorBD.isPresent()){
                                autorNuevo = autorBD.get();
                                autorNuevo.setNombreAutor(autorAPI.nombre());
                                autorNuevo.setNacimientoAutor(autorAPI.nacimiento());
                                autorNuevo.setFallecimientoAutor(autorAPI.fallecimiento());
                            } else {
                                autorNuevo.setNombreAutor(autorAPI.nombre());
                                autorNuevo.setNacimientoAutor(autorAPI.nacimiento());
                                autorNuevo.setFallecimientoAutor(autorAPI.fallecimiento());
                            }
                            listaAutores.add(autorNuevo);
                        }
                        libroEnBD.setAutores(listaAutores);
                        biblioteca.setLibros(List.of(libroEnBD)); // list.of nos permite castear la clase libro por una lista
                        bibliotecaRepository.save(biblioteca);
                    } else {
                        //System.out.println("\nLibro: " + nroLibro + " No se existe en la base datos\n");
                        biblioteca = bibliotecaRepository.findByNombreBiblioteca("Biblioteca-Gutendex");
                        if(biblioteca==null){ // Si es nula la biblioteca es nueva, por lo tanto el libro y sus autores igual
                            //System.out.println("Biblioteca No existe, procedemos a guardar los registros de la biblioteca, su libro y los autores\n");
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
                                libroNuevo.setLenguajes(l.lenguajes().toString());
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
                            //System.out.println("\nBiblioteca Existe, procedemos a guardar los registros su libro y los autores\n");
                            List<Libro> listaNuevoLibro = new ArrayList<>();
                            for(LibroRecord l : libroEnAPI.stream().toList()){
                                Libro libroNuevo = new Libro();
                                libroNuevo.setContadorDescargasLibro(l.contadorDescargas());
                                libroNuevo.setDerechosAutorLibro(l.derechosAutor());
                                libroNuevo.setIdLibro(l.idInterno()); libroNuevo.setTituloLibro(l.titulo());
                                libroNuevo.setLenguajes(l.lenguajes().toString());
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
                    System.out.println("\n*** Biblioteca, Libro y Autores, actualizados satisfactoriamente en Base de Datos\n");
                    //presioneEnterParaContinuar(teclado);
                } else {
                    System.out.println("Nro de libro no se encuentra en la consulta, verifique el listado en pantalla, e intentelo de nuevo");
                    //presioneEnterParaContinuar(teclado);
                }
            } else {
                //System.out.println("\nPresiones  ");
                if(opcionGuardar==2){
                    System.out.println("*** Regresar al Menu Principal");
                } else {
                    System.out.println("Entrada de teclado no valida, intentalo de nuevo");
                }
            }
        } else {
            System.out.println("Entrada de teclado no valida, intentalo de nuevo");
            //presioneEnterParaContinuar(teclado);
            //guardarActualizarBD(datos);
        }
    }

    public void presioneEnterParaContinuar(Scanner teclado){
        System.out.println("\nPresiona Enter para continuar...");
        teclado.nextLine(); // toca ponerlo doble, es un defecto que tiene la clase Scanner.
        teclado.nextLine(); // porque el primer enter de la opcion es tomado por el siguiente nextline, dejando pasar la espera del cursor
    }
}
