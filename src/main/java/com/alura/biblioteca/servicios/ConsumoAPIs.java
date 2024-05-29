package com.alura.biblioteca.servicios;
//
import com.alura.biblioteca.model.Biblioteca;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumoAPIs {
    // Metodo segun la clase realizada en clase
    public String obtenerDatosAPI(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        //HttpResponse<Bibliteca> response = null; sera posible realizar de una vez el paso de la informa una clase Biblioteca?
        try{
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException e){
            throw new RuntimeException(e);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        String json = response.body();
        return json;
    }

     // el siguiente codigo funciona muy bien, en el anterior lo hacemos segun la clase
    public Biblioteca obtenerDatosApix(String url) throws JsonProcessingException {

        String url_str = "https://gutendex.com/books/";
        //
        URI direccion = URI.create(url_str);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
        HttpResponse<String> responsehttp = null;

        try {
            responsehttp = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Podemos usar esta forma de leer la respuesta de la API o lo hacemos como en clase de Genesis
        ObjectMapper objectMapper = new ObjectMapper();
        Biblioteca responsex = null;
        responsex = objectMapper.readValue(responsehttp.body(), Biblioteca.class);
        //
        return responsex;
    }



    public String obtenerDApi(String url) {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e){
            throw new RuntimeException(e);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        String json = response.body();
        return  json;
    }

    public static Map<String, String> apiRtaMap(String url) throws NoSuchFieldException, IllegalAccessException {

        Map<String, String> listaMap = new HashMap<>();
        listaMap.put("count","");
        listaMap.put("next","");
        //listaMap.put("conversion_rate","");
        //String url_str ="https://v6.exchangerate-api.com/v6/807d2718da24424737a4e2ef/pair/" + opcionSeleccionada;
        //
        URI direccion = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
        //
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Lista antes del Gson"+listaMap);
            Gson g = new Gson();
            System.out.println("body" + response.body());
            listaMap = g.fromJson(response.body(),  listaMap.getClass());
            System.out.println("Lista despues del Gson "+listaMap);
            System.out.println();

            return new Gson().fromJson(response.body(),  listaMap.getClass());
            ///
            //listaMap = gson.fromJson(response.body(), listaMap.getClass());
            //return  listaMap;
        } catch (Exception e){
            throw new RuntimeException("No hay registros para esta busqueda. " + e.getMessage() + e.getLocalizedMessage());
        }
    }

    public static List<Object> listaArray2(String url) {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e){
            throw new RuntimeException(e);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        String json = response.body();
        System.out.println("String json "+json);

        // el siguiente codigo funciona
        //String jsonString = "{\"count\":\"1000\",\"results\": [{\"id\": 1, \"name\": \"Producto A\"}, {\"id\": 2, \"name\": \"Producto B\"}, {\"id\": 3, \"name\": \"Producto C\"}]}";
        JSONObject jsonObject = new JSONObject(json);
        // Obtener el array "results"
        JSONArray resultsArray = jsonObject.getJSONArray("results");
        //System.out.println("resultsArray " + resultsArray.toString());  // mejorar y seria pasar directamente a un Map, List o ArrayList segun sea el caso.

        return resultsArray.toList();
    }
}
