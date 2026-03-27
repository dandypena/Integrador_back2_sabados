package com.example.FrankySabado.dtos;

import com.example.FrankySabado.ayudas.Roles;

/**
 * DTO para respuesta de login exitoso
 */
public class LoginResponseDTO {
    
    private Integer id;
    private String nombre;
    private String correo;
    private Roles rol;
    private String message;
    private boolean success;
    private Integer materiaId;
    private String materiaNombre;
    private Integer estudianteId; // ID del registro estudiante (para consultar notas)
    private Integer docenteId; // ID del registro docente (para consultar notas asignadas)

    // Constructores
    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Integer id, String nombre, String correo, Roles rol, String message, boolean success) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.message = message;
        this.success = success;
    }

    public LoginResponseDTO(Integer id, String nombre, String correo, Roles rol, String message, boolean success, Integer materiaId, String materiaNombre) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.message = message;
        this.success = success;
        this.materiaId = materiaId;
        this.materiaNombre = materiaNombre;
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

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(Integer materiaId) {
        this.materiaId = materiaId;
    }

    public String getMateriaNombre() {
        return materiaNombre;
    }

    public void setMateriaNombre(String materiaNombre) {
        this.materiaNombre = materiaNombre;
    }

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Integer getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }
}
