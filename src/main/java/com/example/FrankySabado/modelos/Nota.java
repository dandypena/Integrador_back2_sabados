package com.example.FrankySabado.modelos;

import com.example.FrankySabado.ayudas.TipoEvaluacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notas")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Enumerated(EnumType.STRING)
    private TipoEvaluacion tipo;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "fk_estudiante", referencedColumnName = "id")
    @JsonBackReference(value = "relacionestudiantenota")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "fk_materia", referencedColumnName = "id")
    private Materia materia;

    @Column(name = "comentario", length = 1000)
    private String comentario; // <-- campo opcional para observaciones del docente

    // 🔹 Constructores
    public Nota() {}

    public Nota(Integer id, Double valor, TipoEvaluacion tipo, LocalDate fecha) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    // 🔹 Getters y Setters
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante; //
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
