package com.api_videojuego.dto.videojuego;

import java.math.BigDecimal;

public class DatosVideojuegoTiendaDTO {
	private Integer idVideojuego;
	private String titulo;
	private BigDecimal precio;
	private String imagenPortada;

	public DatosVideojuegoTiendaDTO() {
	}

	public DatosVideojuegoTiendaDTO(Integer idVideojuego, String titulo,
			BigDecimal precio, String imagenPortada) {
		this.idVideojuego = idVideojuego;
		this.titulo = titulo;
		this.precio = precio;
		this.imagenPortada = imagenPortada;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public String getTitulo() {
		return titulo;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public String getImagenPortada() {
		return imagenPortada;
	}

}
