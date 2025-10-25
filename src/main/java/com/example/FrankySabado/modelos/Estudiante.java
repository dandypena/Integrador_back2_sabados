package com.example.FrankySabado.modelos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "promedio", nullable = false, unique = false)
    private Double promedio;
    @Column(name = "fechaNacimiento", nullable = false, unique = false)
    private LocalDate fechaNacimiento;

    //Relacionandome con 1 Usuario
    @OneToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id")
    @JsonManagedReference(value = "relacionestudianteusuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "estudiante")
    @JsonManagedReference(value = "relacionestudianteasistencia")
    private ArrayList<Asistencia> asistencias;

    @OneToMany(mappedBy = "estudiante")
    @JsonManagedReference(value="relacionestudiantenota")
    private ArrayList<Nota> notas;

    @ManyToOne
    @JoinColumn(name = "fk_grupo", referencedColumnName = "id")
    private Grupo grupo;


    public Estudiante() {
    }

    public Estudiante(Integer id, Double promedio, LocalDate fechaNacimiento, Grupo grupo) {
        this.id = id;
        this.promedio = promedio;
        this.fechaNacimiento = fechaNacimiento;
        this.grupo = grupo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Grupo getGrupo() {return grupo;}

    public void setGrupo(Grupo grupo) {this.grupo = grupo;}

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    public ArrayList<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(ArrayList<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
