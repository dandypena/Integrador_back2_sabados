package com.example.FrankySabado.dtos;

public class RankingDTO {
    private String nombreEstudiante;
    private String nombreGrupo;
    private Double promedio;

    public RankingDTO(String nombreEstudiante, String nombreGrupo, Double promedio) {
        this.nombreEstudiante = nombreEstudiante;
        this.nombreGrupo = nombreGrupo;
        this.promedio = promedio;
    }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }

    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}

