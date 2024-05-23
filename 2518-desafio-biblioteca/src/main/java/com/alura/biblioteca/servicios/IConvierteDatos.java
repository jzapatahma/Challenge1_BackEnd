package com.alura.biblioteca.servicios;

public interface IConvierteDatos {
    // Creamos un metodo generico
    <T> T obtenerDatos(String  json, Class<T> clase);
}
