package com.example.FrankySabado.dtos;

public class TipoPromedioDTO {
	private String tipo;
	private Double promedio;
	private int numeroMaterias;

	public TipoPromedioDTO(String tipo, Double promedio, int numeroMaterias) {
		this.tipo = tipo;
		this.promedio = promedio;
		this.numeroMaterias = numeroMaterias;
	}

	public String getTipo() {
		return tipo;
	}

	public Double getPromedio() {
		return promedio;
	}

	public int getNumeroMaterias() {
		return numeroMaterias;
	}
}