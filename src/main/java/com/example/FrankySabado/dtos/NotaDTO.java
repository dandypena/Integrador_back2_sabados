package com.example.FrankySabado.dtos;

import com.example.FrankySabado.ayudas.TipoEvaluacion;
import java.time.LocalDate;

public class NotaDTO {
    private Integer id;
    private Double valor;
    private TipoEvaluacion tipo;
    private String comentario;
    private Integer estudianteId;
    private LocalDate fecha;

    // Constructor por defecto
    public NotaDTO() {}

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoEvaluacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvaluacion tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
