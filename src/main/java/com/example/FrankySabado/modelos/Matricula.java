package com.example.FrankySabado.modelos;

import jakarta.persistence.*;
@Entity
@Table(name = "matriculas", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"estudiante_id", "materia_id", "grupo_id", "periodo"})
})
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "estudiante_id", referencedColumnName = "id")
	private Estudiante estudiante;

	@ManyToOne(optional = false)
	@JoinColumn(name = "materia_id", referencedColumnName = "id")
	private Materia materia;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grupo_id", referencedColumnName = "id")
	private Grupo grupo;

	@Column(nullable = false)
	private String periodo;

	public Long getId() {
		return id;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public Materia getMateria() {
		return materia;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
}



