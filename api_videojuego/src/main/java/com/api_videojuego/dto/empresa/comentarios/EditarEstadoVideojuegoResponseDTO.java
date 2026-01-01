package com.api_videojuego.dto.empresa.comentarios;

public class EditarEstadoVideojuegoResponseDTO {
	private Integer idVideojuego;
	private String titulo;
	private boolean estadoComentarioVideojuego;

	public EditarEstadoVideojuegoResponseDTO(Integer idVideojuego, String titulo,
			boolean estadoComentarioVideojuego) {
		this.idVideojuego = idVideojuego;
		this.titulo = titulo;
		this.estadoComentarioVideojuego = estadoComentarioVideojuego;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public String getTitulo() {
		return titulo;
	}

	public boolean isEstadoComentarioVideojuego() {
		return estadoComentarioVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setEstadoComentarioVideojuego(
			boolean estadoComentarioVideojuego) {
		this.estadoComentarioVideojuego = estadoComentarioVideojuego;
	}

}
