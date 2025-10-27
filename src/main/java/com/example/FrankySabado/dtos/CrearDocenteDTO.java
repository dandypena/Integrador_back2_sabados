package com.example.FrankySabado.dtos;

import com.example.FrankySabado.ayudas.Departamentos;
import com.example.FrankySabado.ayudas.Especialidad;
import com.example.FrankySabado.ayudas.NivelAcademico;

public class CrearDocenteDTO {
    private String nombre;
    private String correo;
    private String contraseña;
    private Especialidad especialidad;
    private NivelAcademico nivelAcademico;
    private Departamentos departamento;

    public CrearDocenteDTO() {
    }

    public CrearDocenteDTO(String nombre, String correo, String contraseña, Especialidad especialidad,
                          NivelAcademico nivelAcademico, Departamentos departamento) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.especialidad = especialidad;
        this.nivelAcademico = nivelAcademico;
        this.departamento = departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public Departamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamentos departamento) {
        this.departamento = departamento;
    }
}

