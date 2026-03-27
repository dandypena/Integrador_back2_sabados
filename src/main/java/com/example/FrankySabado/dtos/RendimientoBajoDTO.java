package com.example.FrankySabado.dtos;

public class RendimientoBajoDTO {

    private String nombreEstudiante;
    private String tipoEvaluacion;
    private Double promedio;
    private String recomendacion;

    public RendimientoBajoDTO() {}

    public RendimientoBajoDTO(String nombreEstudiante, String tipoEvaluacion, Double promedio, String recomendacion) {
        this.nombreEstudiante = nombreEstudiante;
        this.tipoEvaluacion = tipoEvaluacion;
        this.promedio = promedio;
        this.recomendacion = recomendacion;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public void setTipoEvaluacion(String tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }
}


