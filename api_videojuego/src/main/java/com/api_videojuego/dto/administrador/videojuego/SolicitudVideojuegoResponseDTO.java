package com.api_videojuego.dto.administrador.videojuego;

public class SolicitudVideojuegoResponseDTO {
	private Integer idSolicitud;
	private Integer idVideojuego;
	private String nombreVideojuego;
	private String nombreCategoria;
	private String estadoSolicitud;

	public SolicitudVideojuegoResponseDTO() {
	}

	public SolicitudVideojuegoResponseDTO(Integer idSolicitud,
			Integer idVideojuego, String nombreVideojuego, String nombreCategoria,
			String estadoSolicitud) {
		this.idSolicitud = idSolicitud;
		this.idVideojuego = idVideojuego;
		this.nombreVideojuego = nombreVideojuego;
		this.nombreCategoria = nombreCategoria;
		this.estadoSolicitud = estadoSolicitud;
	}

	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public String getNombreVideojuego() {
		return nombreVideojuego;
	}

	public void setNombreVideojuego(String nombreVideojuego) {
		this.nombreVideojuego = nombreVideojuego;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

}
