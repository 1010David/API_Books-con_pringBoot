package com.curosalura.desafio.principal;

import com.curosalura.desafio.model.Datos;
import com.curosalura.desafio.model.DatosLibros;
import com.curosalura.desafio.service.ConsumoAPI;
import com.curosalura.desafio.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void showMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obteberDatos(json, Datos.class);
        System.out.println(datos);

        // Top 10 more downloaded

        System.out.println("Top 10 downloaded");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescarga).reversed())
                .limit(10)
                .map(l-> l.titulo().toUpperCase())
                .forEach(System.out::println);



        //Busqueda por nameBook

        System.out.println("Libro por buscar: ");

        var titulo = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + titulo.replace(" ","+"));
        var datosBusqueda = conversor.obteberDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l-> l.titulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("Found");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Not found");
        }

        //Trabajemos con stadistics
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d-> d.numeroDescarga() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescarga));
        System.out.println("Descargas prom" + est.getAverage());
        System.out.println("Descargas max" + est.getMax());
        System.out.println("Descargas min" + est.getMin());
        System.out.println("Registros Evaluados" + est.getCount());





    }

}















