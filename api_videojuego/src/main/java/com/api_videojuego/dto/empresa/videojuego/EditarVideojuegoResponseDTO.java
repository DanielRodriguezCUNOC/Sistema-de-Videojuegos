package com.api_videojuego.dto.empresa.videojuego;

import java.math.BigDecimal;

public class EditarVideojuegoResponseDTO {
	private String titulo;
	private String descripcion;
	private BigDecimal precio;
	private String recursosMinimos;

	public EditarVideojuegoResponseDTO(String titulo, String descripcion,
			BigDecimal precio, String recursosMinimos) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.recursosMinimos = recursosMinimos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getRecursosMinimos() {
		return recursosMinimos;
	}

	public void setRecursosMinimos(String recursosMinimos) {
		this.recursosMinimos = recursosMinimos;
	}
}
