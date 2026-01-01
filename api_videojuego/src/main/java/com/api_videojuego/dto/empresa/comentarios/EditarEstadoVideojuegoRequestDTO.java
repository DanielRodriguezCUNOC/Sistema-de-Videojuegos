package com.api_videojuego.dto.empresa.comentarios;

public class EditarEstadoVideojuegoRequestDTO {

	private Integer idVideojuego;
	private boolean estadoComentarioVideojuego;

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public boolean getEstadoComentarioVideojuego() {
		return estadoComentarioVideojuego;
	}

	public void setEstadoComentarioVideojuego(
			boolean estadoComentarioVideojuego) {
		this.estadoComentarioVideojuego = estadoComentarioVideojuego;
	}

	public boolean isValido() {
		return idVideojuego != null;
	}

}
