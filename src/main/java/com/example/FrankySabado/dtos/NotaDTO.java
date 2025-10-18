package com.example.FrankySabado.dtos;

import com.example.FrankySabado.ayudas.TipoEvaluacion;
import java.time.LocalDate;

public class NotaDTO {
    private Integer id;
    private Double valor;
    private TipoEvaluacion tipo;
    private LocalDate fecha;
    private Integer estudianteId;

    // Constructores
    public NotaDTO() {}

    public NotaDTO(Integer id, Double valor, TipoEvaluacion tipo, LocalDate fecha, Integer estudianteId) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.fecha = fecha;
        this.estudianteId = estudianteId;
    }

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }
}

