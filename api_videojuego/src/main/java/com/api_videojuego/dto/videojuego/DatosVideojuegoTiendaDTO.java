package com.api_videojuego.dto.videojuego;

import java.math.BigDecimal;

public class DatosVideojuegoTiendaDTO {
	private Integer idVideojuego;
	private String titulo;
	private BigDecimal precio;
	private String imagenPortada;
	private byte[] imagenPortadaBytes;

	public DatosVideojuegoTiendaDTO() {
	}

	public DatosVideojuegoTiendaDTO(Integer idVideojuego, String titulo,
			BigDecimal precio, byte[] imagenPortadaBytes) {
		this.idVideojuego = idVideojuego;
		this.titulo = titulo;
		this.precio = precio;
		this.imagenPortadaBytes = imagenPortadaBytes;
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

	public void setImagenPortada(String imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

	public byte[] getImagenPortadaBytes() {
		return imagenPortadaBytes;
	}

}
