package com.api_videojuego.dto.empresa.videojuego;

public class EditarEstadoVideojuegoDTO {
	private Integer idVideojuego;
	private Boolean nuevoEstado;

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public Boolean getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(Boolean nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public boolean datosValidos() {
		return idVideojuego != null && nuevoEstado != null;
	}
}
