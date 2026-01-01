package com.api_videojuego.dto.videojuego;

public class CalificacionVideojuegoRequestDTO {

	private Integer idVideojuego;
	private Integer idUsuario;
	private Integer calificacion;

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	public boolean isValid() {
		return idVideojuego != null && idUsuario != null && calificacion != null
				&& calificacion >= 1 && calificacion <= 5;
	}
}
