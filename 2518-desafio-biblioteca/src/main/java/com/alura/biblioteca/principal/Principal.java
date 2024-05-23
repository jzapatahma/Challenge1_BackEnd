package com.alura.biblioteca.principal;


import com.alura.biblioteca.servicios.ConsumoAPIs;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPIs Api = new ConsumoAPIs();
    private final String URL_BASE =  "https://gutendex.com/books/";
    private final String API_KEY = "Key"; // en este caso no se requiere la API es publica.

    public  void mostarMenu() {
        var opcion = -1;
        System.out.println("\n *** Bienvenido - Biblioteca Virtual *** \n");
        while(opcion != 0){
            var menu = """
                        1 - buscar libro por titulo
                        2 - listar libros registrados
                        3 - listar autores registrados
                        4 - listar autores vivos en un determinado año
                        5 - listar libros por idioma
                        6 - listar todo
                    
                        0 - salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    listar_todo();
                    break;
                case 2:
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    break;
                case 6:
                    listar_libros();
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

    public void listar_todo(){
        System.out.println(" --> Listando todos los libros <--");
        ConsumoAPIs cApi = new ConsumoAPIs();
        try {
            var json = cApi.obtenerDatosApi(URL_BASE);
            json.getResults().forEach(System.out::println);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void listar_libros(){
//        System.out.println(" --> Listando todos los libros <--");
//        var json = Api.obtenerDatosApi(URL_BASE);
//        System.out.println("Datos de Json: " + json);
//        ConvertirDatos conversor = new ConvertirDatos();
//        List<LibroRecord> libros = new ArrayList<>();
//        var datos = conversor.obtenerDatos(json, libros.getClass());
//        System.out.println("Datos finales: " + datos);
    }

    public void listar_librosMap(){
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

    public void listar_librosArray(){
        System.out.println(ConsumoAPIs.listaArray2(URL_BASE));

    }
}
