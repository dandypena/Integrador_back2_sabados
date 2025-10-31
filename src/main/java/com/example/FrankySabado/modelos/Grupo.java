package com.example.FrankySabado.modelos;
import jakarta.persistence.*;

@Entity
@Table(name = "grupos")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = true)
    private Integer semestre;

    public Grupo() {}

    public Grupo(long id, String nombre, String descripcion, Integer semestre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.semestre = semestre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }
}