package com.example.FrankySabado.dtos;

import com.example.FrankySabado.ayudas.TipoEvaluacion;
import java.util.List;

public class RegistrarNotasDTO {

    private Integer estudianteId;
    private List<NotaIndividualDTO> notas;

    public static class NotaIndividualDTO {
        private TipoEvaluacion tipo;
        private Double valor;

        public TipoEvaluacion getTipo() { return tipo; }
        public void setTipo(TipoEvaluacion tipo) { this.tipo = tipo; }
        public Double getValor() { return valor; }
        public void setValor(Double valor) { this.valor = valor; }
    }

    public Integer getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Integer estudianteId) { this.estudianteId = estudianteId; }
    public List<NotaIndividualDTO> getNotas() { return notas; }
    public void setNotas(List<NotaIndividualDTO> notas) { this.notas = notas; }
}

