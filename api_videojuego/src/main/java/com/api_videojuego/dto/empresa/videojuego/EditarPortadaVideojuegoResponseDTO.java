package com.api_videojuego.dto.empresa.videojuego;

public class EditarPortadaVideojuegoResponseDTO {
	private Integer idVideojuego;
	private String portada;

	public EditarPortadaVideojuegoResponseDTO(Integer idVideojuego,
			String portada) {
		this.idVideojuego = idVideojuego;
		this.portada = portada;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}
}
