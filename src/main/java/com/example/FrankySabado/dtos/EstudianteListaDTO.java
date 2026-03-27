package com.example.FrankySabado.dtos;

import java.time.LocalDate;

/**
 * DTO para listar estudiantes
 * Usado por docentes para ver lista de estudiantes disponibles
 */
public class EstudianteListaDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private LocalDate fechaNacimiento;
    private Double promedio;
    private Integer usuarioId;
    private String grupoNombre;

    // Constructor vacío
    public EstudianteListaDTO() {}

    // Constructor completo
    public EstudianteListaDTO(Integer id, String nombre, String correo, LocalDate fechaNacimiento, 
                             Double promedio, Integer usuarioId, String grupoNombre) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.promedio = promedio;
        this.usuarioId = usuarioId;
        this.grupoNombre = grupoNombre;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }
}
