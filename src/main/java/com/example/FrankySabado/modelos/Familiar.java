package com.example.FrankySabado.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "familiares")
public class Familiar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parentesco", length = 50, nullable = false)
    private String parentesco;

    @Column(name = "telefono", length = 20)
    private String telefono;

    // Relación con Usuario
    @OneToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id")
    @JsonBackReference(value = "relacionfamiliarusuario")
    private Usuario usuario;

    // Relación con Estudiante (un familiar puede estar vinculado a un estudiante)
    @ManyToOne
    @JoinColumn(name = "fk_estudiante", referencedColumnName = "id")
    private Estudiante estudianteVinculado;

    // Constructores
    public Familiar() {}

    public Familiar(Integer id, String parentesco, String telefono) {
        this.id = id;
        this.parentesco = parentesco;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estudiante getEstudianteVinculado() {
        return estudianteVinculado;
    }

    public void setEstudianteVinculado(Estudiante estudianteVinculado) {
        this.estudianteVinculado = estudianteVinculado;
    }
}

