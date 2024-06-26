package com.alura.biblioteca.principal;
//
import com.alura.biblioteca.model.Biblioteca;
import com.alura.biblioteca.model.Libro;
import com.alura.biblioteca.repository.BibliotecaRepository;
import com.alura.biblioteca.repository.LibroRepository;
import com.alura.biblioteca.repository.MaestroxRepository;
import com.alura.biblioteca.servicios.ConsumoAPIs;
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
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPIs Api = new ConsumoAPIs();
    private final String URL_BASE =  "https://gutendex.com/books/";
    private final String API_KEY = "Key"; // en este caso no se requiere la API es publica.
    //
    private BibliotecaRepository repoBiblio;
    private LibroRepository repoLibro;
    private MaestroxRepository repoMaestro;
    public Principal(BibliotecaRepository repositoryBiblio, LibroRepository repositoryLibro , MaestroxRepository repositoryMestro) {
        this.repoBiblio = repositoryBiblio;
        this.repoLibro = repositoryLibro;
        this.repoMaestro = repositoryMestro;
    }
    //
    public void mostarMenu() {
        // el siguiente codigo funciona correctamente aunque el objeto respository is null si llevo el mismo
        // codigo a otra clase principal debemos veriguar como hacerlo que funcione adecuadamente.
//        Maestro maestro = new Maestro();
//        maestro.setNombre("Maestro 1");
//        Detalle detalle1 = new Detalle();
//        detalle1.setDescripcion("Detalle 1");
//        Detalle detalle2 = new Detalle();
//        detalle2.setDescripcion("Detalle 2");
//        // Establecer la relación entre maestro y detalles
//        detalle1.setMaestro(maestro);
//        detalle2.setMaestro(maestro);
//        // Agregar detalles a la lista
//        maestro.getDetalles().add(detalle1);
//        maestro.getDetalles().add(detalle2);
//        System.out.println(maestro);
//        repoMaestro.save(maestro); // guarda el registro en maestros
//        maestro.setDetalles(maestro.getDetalles()); // asigna los detalles a cada maestro
//        repoMaestro.save(maestro); // guarda los detalles para cada maestro.

        var opcion = -1;
        System.out.println("\n *** Bienvenido - Biblioteca Virtual *** \n");
        while(opcion != 0){
            var menu = """
                        1 - Buscar Libro por Titulo
                        2 - Listar Libros Registrados
                        3 - Listar Autores Registrados
                        4 - Listar Autores Vivos en un Determinado Año
                        5 - Listar Libros por Idioma
                        6 - Listar Bibioteca en la API
                        7 - Guardar Biblioteca y Libros
                        8 - Listar Biblioteca y Libros en Base de Datos
                    
                        0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    buscarTituloLibro();
                    break;
                case 2:
                    listarLibrosRegristrados();
                    break;
                case 3:
                    listarAutoresRegristrados();
                    break;
                case 4:
                    listarAutoresVivosYear();
                    break;
                case 5:
                    listarLibrosIdioma();
                    break;
                case 6:
                    listarBibliotecaDeLaAPI();
                    break;
                case 7:
                    GuardarBibliotecaLibrosDB();
                    break;
                case 8:
                    listarBibliotecaLibrosDB();
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

    public void buscarTituloLibro(){
        Scanner tituloLibro = new Scanner(System.in);
        System.out.println("Por favor ingrese el titulo del libro a buscar:");
        var titulo = tituloLibro.nextLine();
        titulo = "%20"+titulo.replace(" ","+");
        //
        String x_titulo = URL_BASE+"?search=" + titulo;
        System.out.println(x_titulo);
        var json = Api.obtenerDApi(x_titulo);
        var convertir = new ConvertirDatos();
        Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
        // Imprimimos
        String count = Optional.ofNullable(teca.getCount()).orElse("N/D");
        String next = Optional.ofNullable(teca.getNext()).orElse("N/D");
        String previous = Optional.ofNullable(teca.getPrevious()).orElse("N/D");
        System.out.println("\n *** Datos Generales de la Biblioteca *** ");
            System.out.println("Count: "+count + " Next: "+next+" Previous: "+previous);
        System.out.println("\n *** Listado de Libros x Titulo *** ");
        // Imprimimos en partalla el libro
        teca.getResults().forEach(System.out::println);
        System.out.println("\n");
    }

    public void listarLibrosRegristrados(){
        String x_titulo = URL_BASE+"?copyright=true";
        var json = Api.obtenerDApi(x_titulo);
        var convertir = new ConvertirDatos();
        Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
        // Imprimimos
        String count = Optional.ofNullable(teca.getCount()).orElse("N/D");
        String next = Optional.ofNullable(teca.getNext()).orElse("N/D");
        String previous = Optional.ofNullable(teca.getPrevious()).orElse("N/D");
        System.out.println("\n *** Datos Generales de la Biblioteca *** ");
        System.out.println("Count: "+count + " Next: "+next+" Previous: "+previous);
        System.out.println("\n *** Libro Registrados con Derechos de Autor *** ");
        // Imprimimos en partalla el libro
        teca.getResults().forEach(System.out::println);
        System.out.println("\n");
    }

    public void listarAutoresRegristrados() {
        //
        String x_titulo = URL_BASE + "?copyright=true";
        var json = Api.obtenerDApi(x_titulo);
        var convertir = new ConvertirDatos();
        Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
        // Imprimimos
        String count = Optional.ofNullable(teca.getCount()).orElse("N/D");
        String next = Optional.ofNullable(teca.getNext()).orElse("N/D");
        String previous = Optional.ofNullable(teca.getPrevious()).orElse("N/D");
        System.out.println("\n *** Datos Generales de la Biblioteca *** ");
        System.out.println("Count: " + count + " Next: " + next + " Previous: " + previous);
        System.out.println("\n *** Listado de Autores Registrados *** ");
        //
        Set<String> autoresUnicos = new HashSet<>(); // se asignan los valores de autores evitando su duplicidad
        teca.getResults().stream()
                .flatMap(libro -> libro.getAuthors().stream())
                .filter(autor -> autoresUnicos.add(autor.getName())) // Agregar autor al conjunto, si no está presente
                .forEach(System.out::println);
        System.out.println("\n");
    }

    public void listarAutoresVivosYear(){
        Scanner yearDeterminado = new Scanner(System.in);
        System.out.println("Por favor ingresa el año a buscar:");
        var year = yearDeterminado.nextLine();
        String x_titulo = URL_BASE;
        System.out.println(x_titulo);
        var json = Api.obtenerDApi(x_titulo);
        var convertir = new ConvertirDatos();
        Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
        // Imprimimos
        String count = Optional.ofNullable(teca.getCount()).orElse("N/D");
        String next = Optional.ofNullable(teca.getNext()).orElse("N/D");
        String previous = Optional.ofNullable(teca.getPrevious()).orElse("N/D");
        System.out.println("\n *** Datos Generales de la Biblioteca *** ");
        System.out.println("Count: " + count + " Next: "+next+" Previous: " + previous);
        System.out.println("\n *** Autores Registrados en el Año: " + year  + " *** ");
        // Imprimimos en partalla el libro
        Set<String> autoresUnicos = new HashSet<>();
        teca.getResults()
                .stream()
                .flatMap(a -> a.getAuthors().stream()).distinct()
                .filter(a -> Integer.valueOf(a.getDeath_year()) < Integer.valueOf(year))
                .filter(autor -> autoresUnicos.add(autor.getName()))
                .forEach(System.out::println);
        System.out.println("\n");
    }

    public void listarLibrosIdioma(){

        Scanner idiomaBuscar = new Scanner(System.in);
        System.out.println("Por favor ingresa uno o varios idiomas (en) o (en,fr,es)...");
        var idiomas = idiomaBuscar.nextLine();
        String x_titulo = URL_BASE+"?languages="+ idiomas;
        System.out.println(x_titulo);
        var json = Api.obtenerDApi(x_titulo);
        var convertir = new ConvertirDatos();
        Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
        // Imprimimos
        String count = Optional.ofNullable(teca.getCount()).orElse("N/D");
        String next = Optional.ofNullable(teca.getNext()).orElse("N/D");
        String previous = Optional.ofNullable(teca.getPrevious()).orElse("N/D");
        System.out.println("\n *** Datos Generales de la Biblioteca *** ");
        System.out.println("Count: " + count + " Next: "+next+" Previous: " + previous);
        System.out.println("\n *** Libros por Idioma: " + idiomas  + " *** ");
        // Imprimimos en partalla el libro
        teca.getResults().forEach(System.out::println);
        System.out.println("\n");
//        teca.getResults()
//                .stream()
//                .flatMap(a -> a.getAuthors().stream()).distinct()
//                .collect(Collectors.toList()).forEach(System.out::println);

    }

    public void listarBibliotecaDeLaAPI(){
        var json = Api.obtenerDApi(URL_BASE);
        var convertir = new ConvertirDatos();
        Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
        //
        String count = Optional.ofNullable(teca.getCount()).orElse("N/D");
        String next = Optional.ofNullable(teca.getNext()).orElse("N/D");
        String previous = Optional.ofNullable(teca.getPrevious()).orElse("N/D");
        //
        System.out.println("\n *** Datos Generales de la Biblioteca *** ");
        System.out.println("Count: "+count + " Next: "+next+" Previous: "+previous);
        //
        System.out.println("\n *** Listado Completo de la Libros en API *** ");
        teca.getResults().forEach(System.out::println);
        System.out.println("\n");
    }

    public void GuardarBibliotecaLibrosDB(){
        String x_titulo = URL_BASE;
        System.out.println(x_titulo);
        var json = Api.obtenerDApi(x_titulo);
        var convertir = new ConvertirDatos();
        Biblioteca teca = convertir.obtenerDatos(json, Biblioteca.class);
        // guardar en bd
        repoBiblio.save(teca);
        //
        teca.setLibros(teca.getResults());
        //
        repoBiblio.save(teca);
        System.out.println("Datos guardados en la base de datos de la biblioteca");
    }

    public  void listarBibliotecaLibrosDB(){
        System.out.println("\n *** Bibliotecas *** ");
        List<Biblioteca> bibliotecas = repoBiblio.findAll();
        bibliotecas.stream().sorted(Comparator.comparing((Biblioteca::getId)))
                .forEach(System.out::println);

        System.out.println("\n *** Libros x Biblioteca *** ");
        List<Libro> libros = repoLibro.findAll();
        libros.stream().sorted(Comparator.comparing((Libro::getIdInterno)))
                .forEach(System.out::println);
        System.out.println("\n");
    }

    //Otras maneras de acceder al conversor y traer los datos mapeados en distintos objetos (list, map, arrays..)
    public void listar_librosConLista(){
        //        System.out.println(" --> Listando todos los libros <--");
        //        var json = Api.obtenerDatosApi(URL_BASE);
        //        System.out.println("Datos de Json: " + json);
        //        ConvertirDatos conversor = new ConvertirDatos();
        //        List<LibroRecord> libros = new ArrayList<>();
        //        var datos = conversor.obtenerDatos(json, libros.getClass());
        //        System.out.println("Datos finales: " + datos);
    }
    public void listar_librosConMap(){
        //        System.out.println(" --> Listando todos los libros <--");
        //        Map<String, String> json = null;
        //        try {
        //            json = ConsumoAPIs.apiRtaMap(URL_BASE);
        //        } catch (NoSuchFieldException e) {
        //            throw new RuntimeException(e);
        //        } catch (IllegalAccessException e) {
        //            throw new RuntimeException(e);
        //        }
        //        System.out.println("Datos de Json: " + json);
        ////        ConvertirDatos conversor = new ConvertirDatos();
        ////        List<RecordLibro> libros = new ArrayList<>();
        ////        var datos = conversor.obtenerDatos(json, libros.getClass());
        ////        System.out.println("Datos finales: " + datos);
    }
    public void listar_librosConArray(){
        //System.out.println(ConsumoAPIs.listaArray2(URL_BASE));
    }
}
