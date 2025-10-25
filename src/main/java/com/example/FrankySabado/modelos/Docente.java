package com.example.FrankySabado.modelos;

import com.example.FrankySabado.ayudas.Departamentos;
import com.example.FrankySabado.ayudas.Especialidad;
import com.example.FrankySabado.ayudas.NivelAcademico;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "docente")

public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "especialidad", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Column(name = "nivelAcademico", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private NivelAcademico nivelAcademico;

    @Column(name = "departamentos", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private Departamentos departamento;

    //Relacion con 1 Usuario
    @OneToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id")
    @JsonManagedReference(value = "relaciondocenteousuario")
    private Usuario usuario;

    // Relación con Materia (OneToMany)
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Materia> materias;


    public Docente() {

    }

    public Docente(Integer id, Especialidad especialidad, NivelAcademico nivelAcademico, Departamentos departamento, Usuario usuario, List<Materia> materias) {
        this.id = id;
        this.especialidad = especialidad;
        this.nivelAcademico = nivelAcademico;
        this.departamento = departamento;
        this.usuario = usuario;
        this.materias = materias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public NivelAcademico getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(NivelAcademico nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Departamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamentos departamento) {
        this.departamento = departamento;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}