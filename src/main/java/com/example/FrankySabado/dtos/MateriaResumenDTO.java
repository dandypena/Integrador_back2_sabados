package com.example.FrankySabado.dtos;

import java.util.List;

public class MateriaResumenDTO {
    private Integer id;
    private String nombre;
    private double promedio;
    private List<NotaDTO> notas;

    public MateriaResumenDTO(Integer id, String nombre, double promedio, List<NotaDTO> notas) {
        this.id = id;
        this.nombre = nombre;
        this.promedio = promedio;
        this.notas = notas;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public List<NotaDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTO> notas) {
        this.notas = notas;
    }

    // Alias para compatibilidad con código existente
    public List<NotaDTO> getUltimasNotas() {
        return notas;
    }
}
