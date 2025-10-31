package com.example.FrankySabado.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

    @Entity
    @Table(name = "materias")
    public class Materia {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @NotBlank
        @Size(max = 100)
        @Column(name = "nombre", nullable = false)
        private String nombre;

        @NotBlank(message = "El código es obligatorio")
        @Size(max = 50)
        @Column(name = "codigo", nullable = false, unique = true)
        private String codigo;

        @Column(name = "creditos")
        private Integer creditos;

        @Column(name = "descripcion", length = 500)
        private String descripcion;


        public Materia() {
        }

        public Materia(Integer id, String nombre, String codigo, Integer creditos, String descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.codigo = codigo;
            this.creditos = creditos;
            this.descripcion = descripcion;
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

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public Integer getCreditos() {
            return creditos;
        }

        public void setCreditos(Integer creditos) {
            this.creditos = creditos;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }



