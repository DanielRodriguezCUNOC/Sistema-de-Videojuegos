package com.api_videojuego.dto.empresa.videojuego;

public class VideojuegoGestionRequestDTO {
	private Integer idVideojuego;
	private String titulo;
	private boolean estado;

	public VideojuegoGestionRequestDTO() {
	}

	public VideojuegoGestionRequestDTO(Integer idVideojuego, String titulo,
			boolean estado) {
		this.idVideojuego = idVideojuego;
		this.titulo = titulo;
		this.estado = estado;
	}

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

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
