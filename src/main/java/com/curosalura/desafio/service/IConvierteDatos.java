package com.curosalura.desafio.service;

public interface IConvierteDatos {

    <T> T obteberDatos(String json, Class<T> clase);

}
