package com.api_videojuego.dto.empresa.videojuego;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

public class VideojuegoResponseDTO {

	private Integer idVideojuego;
	private String titulo;
	private String descripcion;
	private LocalDate fechaLanzamiento;
	private BigDecimal precio;
	private String recursosMinimos;
	private boolean estado;
	private InputStream imagenUrl;

	public VideojuegoResponseDTO() {
	}

	public VideojuegoResponseDTO(Integer idVideojuego, String titulo,
			String descripcion, LocalDate fechaLanzamiento, BigDecimal precio,
			String recursosMinimos, boolean estado, InputStream imagenUrl) {
		this.idVideojuego = idVideojuego;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaLanzamiento = fechaLanzamiento;
		this.precio = precio;
		this.recursosMinimos = recursosMinimos;
		this.estado = estado;
		this.imagenUrl = imagenUrl;
	}

	// * Getters and Setters */

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
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

	public LocalDate getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
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

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public InputStream getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(InputStream imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

}
