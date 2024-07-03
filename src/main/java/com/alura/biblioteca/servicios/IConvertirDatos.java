package com.alura.biblioteca.servicios;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
