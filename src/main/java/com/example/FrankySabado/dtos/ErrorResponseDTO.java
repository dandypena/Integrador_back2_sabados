package com.example.FrankySabado.dtos;

/**
 * DTO para respuesta de error
 */
public class ErrorResponseDTO {
    
    private boolean success;
    private String error;
    private String message;

    // Constructores
    public ErrorResponseDTO() {
        this.success = false;
    }

    public ErrorResponseDTO(String error, String message) {
        this.success = false;
        this.error = error;
        this.message = message;
    }

    public ErrorResponseDTO(String message) {
        this.success = false;
        this.message = message;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
