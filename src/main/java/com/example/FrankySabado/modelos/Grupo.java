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

    @Column(nullable = false)
    private int semestre;

    @Column(name ="materia-id", nullable = false)
    private long materiaId;

    public Grupo() {}

    public Grupo(long id, String nombre, int semestre, long materiaId) {
        this.id = id;
        this.nombre = nombre;
        this.semestre = semestre;
        this.materiaId = materiaId;
    }

    public long getMateriaId() {
        return materiaId;
    }

    public int getSemestre() {
        return semestre;
    }

    public java.lang.String getNombre() {
        return nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setMateriaId(long materiaId) {
        this.materiaId = materiaId;
    }
}