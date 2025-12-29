package com.api_videojuego.dto.administrador.videojuego;

public class SolicitudVideojuegoRequestDTO {
	private Integer idSolicitud;
	private Integer idVideojuego;
	private String categoria;
	private String estado;

	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public boolean isValid() {
		return idSolicitud != null && idVideojuego != null && categoria != null
				&& estado != null;
	}

}
