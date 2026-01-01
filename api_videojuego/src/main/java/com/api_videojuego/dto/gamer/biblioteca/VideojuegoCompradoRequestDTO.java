package com.api_videojuego.dto.gamer.biblioteca;

public class VideojuegoCompradoRequestDTO {

	private Integer idVideojuego;
	private Integer idUsuario;
	private boolean estadoInstalacion;

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

	public boolean isEstadoInstalacion() {
		return estadoInstalacion;
	}

	public void setEstadoInstalacion(boolean estadoInstalacion) {
		this.estadoInstalacion = estadoInstalacion;
	}

	public boolean isValid() {
		return idVideojuego != null && idUsuario != null;
	}

}
