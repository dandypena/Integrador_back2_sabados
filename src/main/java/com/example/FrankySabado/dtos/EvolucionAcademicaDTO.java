package com.example.FrankySabado.dtos;

import java.util.List;

public class EvolucionAcademicaDTO {
    private String periodo;
    private Integer anio; // renombrado desde 'año' a 'anio' para evitar caracteres no-ASCII
    private Integer semestre;
    private Double promedioGeneral;
    private Integer cantidadNotas;
    private List<NotaDTO> notas;

    // Constructores
    public EvolucionAcademicaDTO() {}

    public EvolucionAcademicaDTO(String periodo, Integer anio, Integer semestre, Double promedioGeneral, Integer cantidadNotas, List<NotaDTO> notas) {
        this.periodo = periodo;
        this.anio = anio;
        this.semestre = semestre;
        this.promedioGeneral = promedioGeneral;
        this.cantidadNotas = cantidadNotas;
        this.notas = notas;
    }

    // Getters y Setters
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Double getPromedioGeneral() {
        return promedioGeneral;
    }

    public void setPromedioGeneral(Double promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }

    public Integer getCantidadNotas() {
        return cantidadNotas;
    }

    public void setCantidadNotas(Integer cantidadNotas) {
        this.cantidadNotas = cantidadNotas;
    }

    public List<NotaDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTO> notas) {
        this.notas = notas;
    }
}
