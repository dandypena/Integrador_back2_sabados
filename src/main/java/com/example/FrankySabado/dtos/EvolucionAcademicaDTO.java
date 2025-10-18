package com.example.FrankySabado.dtos;

import java.util.List;

public class EvolucionAcademicaDTO {
    private String periodo;
    private Integer año;
    private Integer semestre;
    private Double promedioGeneral;
    private Integer cantidadNotas;
    private List<NotaDTO> notas;

    // Constructores
    public EvolucionAcademicaDTO() {}

    public EvolucionAcademicaDTO(String periodo, Integer año, Integer semestre, Double promedioGeneral, Integer cantidadNotas, List<NotaDTO> notas) {
        this.periodo = periodo;
        this.año = año;
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

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
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

