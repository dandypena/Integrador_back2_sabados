package com.example.FrankySabado.dtos;

import java.util.List;

public class ResumenAcademicoDTO {
    private Integer estudianteId;
    private String nombreEstudiante;
    private double promedioGeneral;
    private List<MateriaResumenDTO> materias;

    public ResumenAcademicoDTO(Integer estudianteId, String nombreEstudiante, double promedioGeneral, List<MateriaResumenDTO> materias) {
        this.estudianteId = estudianteId;
        this.nombreEstudiante = nombreEstudiante;
        this.promedioGeneral = promedioGeneral;
        this.materias = materias;
    }

    // Getters y Setters
    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public double getPromedioGeneral() {
        return promedioGeneral;
    }

    public void setPromedioGeneral(double promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }

    public List<MateriaResumenDTO> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaResumenDTO> materias) {
        this.materias = materias;
    }
}
