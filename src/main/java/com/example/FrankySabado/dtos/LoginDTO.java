package com.example.FrankySabado.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitud de login
 */
public class LoginDTO {
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String contraseña;

    // Constructores
    public LoginDTO() {
    }

    public LoginDTO(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    // Getters y Setters
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
}
